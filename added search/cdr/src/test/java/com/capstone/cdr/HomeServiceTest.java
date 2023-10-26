package com.capstone.cdr;

import com.capstone.cdr.dto.NormalCallForm;
import com.capstone.cdr.dto.ExcecutionStatus;
import com.capstone.cdr.entity.CDR;
import com.capstone.cdr.entity.Customer;
import com.capstone.cdr.entity.Type;
import com.capstone.cdr.repository.CDRRepository;
import com.capstone.cdr.repository.CustomerRepository;
import com.capstone.cdr.repository.TypeRepository;
import com.capstone.cdr.service.HomeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class HomeServiceTest {

    @InjectMocks
    private HomeService homeService;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CDRRepository cdrRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTypes() {
        // Create a list of types to return when the repository method is called
        List<Type> mockTypeList = new ArrayList<>();
        mockTypeList.add(new Type());
        mockTypeList.add(new Type());
        when(typeRepository.findDifferentTypes()).thenReturn(mockTypeList);

        List<Type> types = homeService.getTypes();
        assertNotNull(types);
        assertEquals(2, types.size());
    }

    @Test
public void testSaveCDRService() {
    // Prepare the input form data
    NormalCallForm form = new NormalCallForm();
    form.setSubscriber(1);
    form.setReciever(2);
    form.setDate("2023-10-20");
    form.setTime("15:30");
    form.setSubscriberLocation("Location1");
    form.setRecieverLocation("Location2");
    form.setCallType("Type1");
    form.setDuration(60);
    form.setHasVoiceMail(false);
    form.setVoiceMailDuration(0);

    
    Type mockType = new Type();
    mockType.setId(1);
    mockType.setType("Type1");
    Optional<Customer> mockSubscriber = Optional.of(new Customer());
    Optional<Customer> mockReciever = Optional.of(new Customer());

    when(typeRepository.findByType("Type1")).thenReturn(mockType);
    when(customerRepository.findById(1)).thenReturn(mockSubscriber);
    when(customerRepository.findById(2)).thenReturn(mockReciever);

   
    ExcecutionStatus status = homeService.saveCDRService(form, false);
    assertNotNull(status);
    assertEquals("added successfully", status.getStatus());

   
    ArgumentCaptor<CDR> captor = ArgumentCaptor.forClass(CDR.class);
    Mockito.verify(cdrRepository).save(captor.capture());


    CDR capturedCDR = captor.getValue();
    assertEquals(mockSubscriber.get(), capturedCDR.getSubscriber());
    assertEquals(mockReciever.get(), capturedCDR.getReciever());
    assertEquals(LocalDate.parse("2023-10-20"), capturedCDR.getDate());
    assertEquals(LocalTime.parse("15:30"), capturedCDR.getTime());
    assertEquals("Location1", capturedCDR.getSubscriberLocation());
    assertEquals("Location2", capturedCDR.getRecieverLocation());
    assertEquals(mockType, capturedCDR.getCallType());
    assertEquals(60, capturedCDR.getDuration());
    assertFalse(capturedCDR.isFailed());
    assertFalse(capturedCDR.isHasVoicemail());
    assertEquals(0, capturedCDR.getVoiceMailDuration());
}

}
