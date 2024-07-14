package com.black_lotus.projectcomet.Models

class Users {
    private var uid : String = ""
    private var n_username : String = ""
    private var email : String = ""
    private var image : String = ""
    private var search : String = ""
    private var fname : String = ""
    private var surname : String = ""
    private var age : String = ""
    private var country : String = ""
    private var state : String = ""
    private var city : String = ""

    constructor()

    constructor(
        uid : String,
        email : String,
        n_username : String,
        image : String,
        search : String,
        fname : String,
        surname : String,
        age : String,
        country : String,
        state : String,
        city : String
    ){
        this.uid = uid
        this.email = email
        this.n_username = n_username
        this.image = image
        this.search = search
        this.fname = fname
        this.surname = surname
        this.age = age
        this.country = country
        this.state = state
        this.city = city

    }

    fun getUid() : String?{
        return uid
    }
    fun setUid(uid : String){
        this.uid = uid
    }
    fun getEmail() : String?{
        return email
    }
    fun setEmail(email : String){
        this.email = email
    }
    fun getN_Username() : String?{
        return n_username
    }
    fun setN_Username(n_username: String){
        this.n_username = n_username
    }
    fun getImage() : String?{
        return image
    }
    fun setImage(image : String){
        this.image = image
    }
    fun getSearch() : String?{
        return search
    }
    fun setSearch(search : String){
        this.search = search
    }
    fun getFname() : String?{
        return fname
    }
    fun setFname(fname : String){
        this.fname = fname
    }
    fun getSurname() : String?{
        return surname
    }
    fun setSurname(surname : String){
        this.surname = surname
    }
    fun getAge() : String?{
        return age
    }
    fun setAge(age : String){
        this.age = age
    }
    fun getCountry() : String?{
        return country
    }
    fun setCountry(country : String){
        this.country = country
    }
    fun getState() : String?{
        return state
    }
    fun setState(state : String){
        this.state = state
    }
    fun getCity() : String?{
        return city
    }
    fun setCity(city : String){
        this.city = city
    }

}