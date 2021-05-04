package com.galvanize.model;

import com.galvanize.annotations2.Init2;
import com.galvanize.annotations2.JsonElement2;
import com.galvanize.annotations2.JsonSerializable2;

@JsonSerializable2
public class Person2 {

    @JsonElement2
    private String firstName;

    @JsonElement2
    private String lastName;

    @JsonElement2(key="personAge")
    private String age;

    private String address;

    @Init2
    private void initNames(){
        this.firstName = this.firstName.substring(0, 1).toUpperCase()
                + this.firstName.substring(1);
        this.lastName = this.lastName.substring(0, 1).toUpperCase()
                + this.lastName.substring(1);
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
