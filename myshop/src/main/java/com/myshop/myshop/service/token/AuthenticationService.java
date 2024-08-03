package com.myshop.myshop.service.token;

import com.myshop.myshop.entity.customer;
import com.myshop.myshop.repository.customerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private customerRepository customerRepository;

    public boolean authenticate(String username, String password) {
        // ค้นหาผู้ใช้จาก username
        customer customer = customerRepository.findByUsername(username)
                .orElse(null);

        // หากไม่พบผู้ใช้ ให้คืนค่า false
        if (customer == null) {
            return false;
        }

        // เปรียบเทียบรหัสผ่านที่กรอกเข้ามากับรหัสผ่านในฐานข้อมูล
        return password.equals(customer.getPassword());
    }
}
