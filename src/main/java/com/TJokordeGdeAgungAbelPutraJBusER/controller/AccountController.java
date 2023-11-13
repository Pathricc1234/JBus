package com.TJokordeGdeAgungAbelPutraJBusER.controller;

import com.TJokordeGdeAgungAbelPutraJBusER.Account;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonTable;
import com.TJokordeGdeAgungAbelPutraJBusER.Renter;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonAutowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/account")
public class AccountController implements BasicGetController<Account>{
    public JsonTable<Account> accountTable;
    public AccountController(){
        try {
            this.accountTable = new JsonTable<>(Account.class,"src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\account.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @JsonAutowired(value = Account.class, filepath = "src\\main\\java\\com\\TJokordeGdeAgungAbelPutraJBusER\\json\\account.json")
    private Account account;

    public JsonTable<Account> getJsonTable(){
        return accountTable;
    }

    @PostMapping("/register")
    BaseResponse<Account> register(@RequestParam String name, @RequestParam String email, @RequestParam String password){
        String generatedPassword;

        if(name.isBlank()){
            return new BaseResponse<>(false,"Nama Kosong",null);
        }
        if(!Account.validate(email,password)){
            return new BaseResponse<>(false,"Format Email atau Password Salah",null);
        }
        boolean emailExists = false;
        for (Account acc : accountTable) {
            if (acc.email.equals(email)) {
                emailExists = true;
                break;
            }
        }
        if (emailExists) {
            return new BaseResponse<>(false, "Email sudah digunakan", null);
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
        Account newAccount = new Account(name,email,generatedPassword);
        accountTable.add(newAccount);

        try {
            accountTable.writeJson();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return new BaseResponse<>(true,"Berhasil Register",newAccount);
    }

    @PostMapping("/login")
    BaseResponse<Account> login(@RequestParam String email, @RequestParam String password) {
        for (Account acc : accountTable) {
            if (acc.email.equals(email)) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getBytes());
                    byte[] bytes = md.digest();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < bytes.length; i++) {
                        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                    }
                    String hashedPassword = sb.toString();

                    if (acc.password.equals(hashedPassword)) {
                        return new BaseResponse<>(true, "Berhasil Login", acc);
                    } else {
                        return new BaseResponse<>(false, "Password Salah", null);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return new BaseResponse<>(false, "Password Salah", null);
                }
            }
        }
        return new BaseResponse<>(false, "Email atau Password Salah", null);
    }

    @PostMapping("/{id}/registerRenter")
    BaseResponse<Renter> registerRenter(@PathVariable int id, @RequestParam String companyName, @RequestParam String address, @RequestParam String phoneNumber){
        Account rentReg = null;
        for (Account acc: accountTable){
            if(acc.id == id){
                rentReg = acc;
                break;
            }
        }

        if(rentReg != null){
            if(rentReg.company == null){
                Renter newRenter = new Renter(companyName,phoneNumber,address);
                rentReg.company = newRenter;
                try {
                    accountTable.writeJson();
                    return new BaseResponse<>(true,"Akun Renter Berhasil Dibuat",rentReg.company);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            else{
                return new BaseResponse<>(false,"Akun Renter Ditemukan",null);
            }
        }
        return new BaseResponse<>(false,"Akun Tidak Ditemukan",null);
    }

    @PostMapping("/{id}/topUp")
    BaseResponse<Double> topUp(@PathVariable int id, @RequestParam double amount){
        Account accTopUp = null;

        for(Account acc: accountTable){
            if(acc.id == id){
                accTopUp = acc;
                break;
            }
        }
        if(accTopUp != null){
            boolean success = accTopUp.topUp(amount);
            if(success){
                try{
                    accountTable.writeJson();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                return new BaseResponse<>(true, "Berhasil Top Up", accTopUp.balance);
            }
            else{
                return new BaseResponse<>(false,"Nominal Tidak Valid", null);
            }
        }
        return new BaseResponse<>(false,"Akun Tidak Ditemukan",null);
    }
}