package com.example.moodyeats


class User () {
    private lateinit var name:String
    private lateinit var email:String
internal constructor(name:String, email:String) : this() {
    this.name=name
    this.email=email
}
public fun setname(name:String)
    {
    this.name=name
}
    public fun setemail(email:String)
    {
        this.email=email
    }
    public fun getemail():String{
        return this.email
    }
    public fun getname():String{
        return this.name
    }
}