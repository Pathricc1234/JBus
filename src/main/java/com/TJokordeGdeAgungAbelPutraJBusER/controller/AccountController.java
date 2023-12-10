package com.TJokordeGdeAgungAbelPutraJBusER.controller;

import com.TJokordeGdeAgungAbelPutraJBusER.Account;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonDBEngine;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonTable;
import com.TJokordeGdeAgungAbelPutraJBusER.Renter;
import com.TJokordeGdeAgungAbelPutraJBusER.dbjson.JsonAutowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * AccountController untuk melakukan backend yang berhubungan dengan Account class
 * @author Tjokorde Gde Agung Abel Putra
 * @version 1.0
 */
@RestController
@RequestMapping("/account")
public class AccountController implements BasicGetController<Account>{
    public JsonTable<Account> accountTable;

    /**
     * Mendapatkan semua data di account Json yang kemudian di simpan di accountTable
     */
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

    /**
     * Melakukan registrasi akun
     * @param name nama akun
     * @param email email akun
     * @param password password akun
     * @return class dari Account dan menyimpan di JSON
     */
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

    /**
     * Mengecek email dan password dan melakukan login jika email dan password pada JSON benar
     * @param email email dari akun yang ingin login
     * @param password password akun dari yang ingin login
     * @return jika benar maka akan memberikan informasi akun yang dilogin
     */
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

    /**
     * Melakukan registrasi renter
     * @param id id dari akun
     * @param companyName nama perusahaan
     * @param address alamat perusahaan
     * @param phoneNumber nomor telepon perusahaan
     * @return Akun renter yang kemudian disimpan di JSON
     */
    @PostMapping("/{id}/registerRenter")
    BaseResponse<Renter> registerRenter(@PathVariable int id, @RequestParam String companyName, @RequestParam String address, @RequestParam String phoneNumber){
        Account rentReg = null;
        for (Account acc: accountTable){
            if(acc.id == id){
                rentReg = acc;
                break;
            }
        }
        if(!Renter.validate(companyName,phoneNumber)){
            return new BaseResponse<>(false,"Format nama atau nomor telepon salah",null);
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

    /**
     * menambah balance pada akun
     * @param id id dari akun
     * @param amount jumlah balance yang ingin di top up
     * @return saldo baru dari akun
     */
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

    /**
     * melakukan pembaruan pada balance (tidak menambahkan melainkan mengganti dengan angka tertentu)
     * @param accountId ID dari akun
     * @param balance jumlah saldo yang ingin diganti
     * @return Account class yang terdapat perubahan di balance yang kemudian disimpan di JSON
     */
    @PostMapping("/updateBalance")
    BaseResponse<Account> updateBalance(@RequestParam int accountId, @RequestParam int balance){
        Account findAcc = null;
        for(Account acc: accountTable){
            if(acc.id == accountId){
                findAcc = acc;
                break;
            }
        }
        if(findAcc != null){
            findAcc.balance = balance;
            try {
                accountTable.writeJson();
                return new BaseResponse<>(true,"Balance Updated",findAcc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new BaseResponse<>(false,"No Account Found",null);
    }

    /**
     * Mengecek apakah password baru sama dengan password sekarang
     * @param email email dari akun
     * @param password password baru akun
     * @return jika tidak sama maka akan mengembalikan true, jika sama maka false
     */
    @GetMapping("/passExist")
    BaseResponse<Boolean> passExist(@RequestParam String email, @RequestParam String password){
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
                        return new BaseResponse<>(true, "Password Ada", true);
                    } else {
                        return new BaseResponse<>(false, "Password Tidak Ada", false);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return new BaseResponse<>(false, "Password Tidak Ada", false);
                }
            }
        }
        return new BaseResponse<>(false, "Email atau Password Salah", null);
    }

    /**
     * mengecek apakah email ada dalam database
     * @param email email yang ingin dicari
     * @return Account dengan email yang diinginkan jika ada, jika tidak maka null
     */
    @GetMapping("/findEmail")
    BaseResponse<Account> accByEmail(@RequestParam String email){
        Account findAccEmail = null;
        for(Account acc: accountTable){
            if(Objects.equals(acc.email,email)){
                return new BaseResponse<>(true,"Akun dengan Email " + email + " Ditemukan",acc);
            }
        }
        return new BaseResponse<>(true,"Akun dengan Email " + email + " Tidak ditemukan",null);
    }

    /**
     * melakukan penggantian password pada akun
     * @param email email dari akun
     * @param newPass password baru akun
     * @return Account dengan passowrd yang telah diganti
     */
    @PostMapping("/resetPassword")
    BaseResponse<Account> resetPassword(@RequestParam String email, @RequestParam String newPass){
        String generatedPassword;
        Account passResAcc = null;
        for (Account acc: accountTable){
            if(Objects.equals(acc.email, email)){
                passResAcc = acc;
                break;
            }
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(newPass.getBytes());
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
        assert passResAcc != null;
        passResAcc.password = generatedPassword;
        try {
            accountTable.writeJson();
            return new BaseResponse<>(true,"Password Berhasil Diubah",passResAcc);
        } catch (IOException e) {
            e.printStackTrace();
            return new BaseResponse<>(false,"Password Gagal Diubah",null);
        }
    }

    /**
     * Mendapatkan semua informsi akun berdasarkan id
     * @param accountId ID akun yang ingin dicari
     * @return jika ID ada maka akan memberikan semua informasi akun jika tidak maka null
     */
    @GetMapping("/getAccount")
    BaseResponse<Account> getRenterAccount(@RequestParam int accountId){
        Account findAcc = null;
        for (Account acc: accountTable){
            if(acc.id == accountId){
                findAcc = acc;
                break;
            }
        }
        if(findAcc == null) {
            return new BaseResponse<>(false, "No Account Found", null);
        }
        return new BaseResponse<>(true,"Account Found",findAcc);
    }
}