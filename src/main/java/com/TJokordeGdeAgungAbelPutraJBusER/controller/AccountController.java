package com.TJokordeGdeAgungAbelPutraJBusER.controller;

import com.TJokordeGdeAgungAbelPutraJBusER.Account;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonAutowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/account")
public class AccountController{

    BaseResponse<Account> register(@RequestParam String name, @RequestParam String email, @RequestParam String password){
     if(name.isBlank()){
         return new BaseResponse<>(false,"Gagal Register",null);
     }
     else{
         return new BaseResponse<>(true,"Berhasil Register",account);
     }
     if(Account.validate(email,password) == false){
         return new BaseResponse<>(false,"Gagal Register",null);
     }
     else{
         return new BaseResponse<>(true,"Berhasil Register",account);
     }
    }
}
