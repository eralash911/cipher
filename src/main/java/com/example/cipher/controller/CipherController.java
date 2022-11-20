package com.example.cipher.controller;

import com.example.cipher.cipher.CipherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cipher")
public class CipherController {

    @PatchMapping("encript")
    public String encript(@RequestBody String plainText){
        return CipherService.encrypt(plainText);
    }

    @PostMapping("decript")
    public String decript(@RequestBody String cipherText){
        return CipherService.decrypt(cipherText);
    }
}
