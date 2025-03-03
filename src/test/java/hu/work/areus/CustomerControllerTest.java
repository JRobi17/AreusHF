package hu.work.areus;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.work.areus.controller.CustomerController;
import hu.work.areus.model.Customer;
import hu.work.areus.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest extends CustomerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String USERNAME = "areus";
    private static final String PASSWORD = "areuspw";

    @Test
    @WithMockUser(username = USERNAME, roles = "USER")
    void testGetCustomerById() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(createDefaultCustomer1());

        mockMvc.perform(get("/areus/customers/getCustomer/1")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Peter"));

        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    void testGetAllCustomers() throws Exception {
        List<Customer> customers = Arrays.asList(createDefaultCustomer1(),
                createDefaultCustomer2(), createDefaultCustomer3());
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/areus/customers/getAllCustomers")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Peter"))
                .andExpect(jsonPath("$[0].email").value("areuspeter@gmail.com"))
                .andExpect(jsonPath("$[1].firstName").value("Luca"))
                .andExpect(jsonPath("$[1].email").value("areusluca@gmail.com"))
                .andExpect(jsonPath("$[2].firstName").value("Balazs"))
                .andExpect(jsonPath("$[2].email").value("areusbalazs@gmail.com"));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    void testCreateCustomer() throws Exception {
        Customer testCustomer = createDefaultCustomer1();
        when(customerService.createCustomer(any(Customer.class))).thenReturn(testCustomer);

        mockMvc.perform(post("/areus/customers/createCustomer")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Peter"))
                .andExpect(jsonPath("$.birthDate").value("1974.12.05"));

        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    void testUpdateCustomer() throws Exception {
        Customer testCustomer = createDefaultCustomer1();
        when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(testCustomer);

        mockMvc.perform(put("/areus/customers/updateCustomer/1")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Peter"))
                .andExpect(jsonPath("$.phoneNumber").value("+36201234567"));

        verify(customerService, times(1)).updateCustomer(eq(1L), any(Customer.class));
    }

    @Test
    @WithMockUser(username = "areus", password = "areuspw", roles = "USER")
    void testDeleteCustomer() throws Exception {
        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/areus/customers/deleteCustomer/1")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomer(1L);
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    void testGetAverageAge() throws Exception {
        when(customerService.getAverageAge()).thenReturn(27.5);

        mockMvc.perform(get("/areus/customers/getCustomersAverageAge")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("27.5"));

        verify(customerService, times(1)).getAverageAge();
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    void testGetCustomersBetween18And40() throws Exception {
        List<Customer> customers = Arrays.asList(createDefaultCustomer2(), createDefaultCustomer3());
        when(customerService.getCustomersBetween18And40()).thenReturn(customers);

        mockMvc.perform(get("/areus/customers/getAllCustomersBetween18And40")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Luca"))
                .andExpect(jsonPath("$[0].lastName").value("Areus"))
                .andExpect(jsonPath("$[1].firstName").value("Balazs"))
                .andExpect(jsonPath("$[1].lastName").value("Areus"));

        verify(customerService, times(1)).getCustomersBetween18And40();
    }
}
