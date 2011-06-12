package org.aphillips.hw.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table( name = "USERS" )
public class User implements Serializable {
  
  private static final long serialVersionUID = -6287414285091689850L;

  private Long id;

  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private Date dob;
  
  @Id
  @GeneratedValue
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  
  @Column(name="firstName", nullable = true, length=20)
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  @Column(name="lastName", nullable = true, length=20)
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  @Column(name="email", nullable = true, length=50)
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  
  @Column(name="phone", nullable = true, length=12)
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  @Temporal(TemporalType.DATE)
  @Column(name = "dob")
  public Date getDob() {
    return dob;
  }
  public void setDob(Date dob) {
    this.dob = dob;
  }
  

}
