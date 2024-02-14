package com.app.taqaseem.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "User")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id;

  @Column(name = "PhoneNumber", unique = true, nullable = false, length = 15)
  private String phoneNumber;

  @Column(name = "Name", length = 255)
  private String name;

  @Temporal(TemporalType.DATE)
  @Column(name = "Birthday")
  private Date birthday;

  @Column(name = "IsRegistered", nullable = false)
  private Boolean isRegistered = false;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CreatedAt", nullable = false)
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "UpdatedAt", nullable = false)
  private Date updatedAt;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return phoneNumber;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
