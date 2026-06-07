package com.monocept.demo.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.monocept.demo.dto.request.PaymentRequestDto;
import com.monocept.demo.dto.response.PaymentResponseDto;
import com.monocept.demo.entity.Policy;
import com.monocept.demo.entity.PremiumPayment;
import com.monocept.demo.enums.PaymentStatus;
import com.monocept.demo.exception.ResourceNotFoundException;
import com.monocept.demo.repository.PolicyRepository;
import com.monocept.demo.repository.PremiumPaymentRepository;
import com.monocept.demo.service.PaymentService;



@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PremiumPaymentRepository paymentRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PaymentResponseDto payPremium(Long policyId,
                                         PaymentRequestDto requestDto) {

        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Policy not found"));

        PremiumPayment payment = new PremiumPayment();

        payment.setPolicy(policy);
        payment.setAmount(requestDto.getAmount());
        payment.setPaymentMode(requestDto.getPaymentMode());
        payment.setPaymentDate(LocalDateTime.now());

        payment.setPaymentStatus(PaymentStatus.SUCCESS);


        payment = paymentRepository.save(payment);

        return mapper.map(payment, PaymentResponseDto.class);
    }

    @Override
    public PaymentResponseDto getPaymentById(Long paymentId) {

        PremiumPayment payment =
                paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                new ResourceNotFoundException("Payment not found"));

        return mapper.map(payment,
                PaymentResponseDto.class);
    }

    @Override
    public Page<PaymentResponseDto> getPaymentsByPolicy(
            Long policyId,
            Pageable pageable) {

        return paymentRepository
                .findByPolicyPolicyId(policyId, pageable)
                .map(payment ->
                        mapper.map(
                                payment,
                                PaymentResponseDto.class));
    }

    @Override
    public Page<PaymentResponseDto> getAllPayments(
            Pageable pageable) {

        return paymentRepository.findAll(pageable)
                .map(payment ->
                        mapper.map(payment,
                                PaymentResponseDto.class));
    }

    @Override
    public PaymentResponseDto updatePaymentStatus(
            Long paymentId,
            String status) {

        PremiumPayment payment =
                paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found"));

        payment.setPaymentStatus(
                PaymentStatus.valueOf(status));

        paymentRepository.save(payment);

        return mapper.map(payment,
                PaymentResponseDto.class);
    }
}