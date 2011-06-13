package org.aphillips.hw.domain;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.aphillips.hw.impl.ValidationError;

public class UserValidator {

  private User user;

  public UserValidator(User user) {
    this.user = user;
  }

  /**
   * This validate method seeks to satisfy the acceptance criteria for a valid user:
   * <ul>
   * <li>At least one of the following fields must exist: First Name, Last Name, Email Address, or Phone Number
   * <li>First and Last Name must be alpha-characters only
   * <li>Email Address must be real email address
   * <li>Phone Number can accept any of the following characters: ()-.0-9
   * <li>Birthday must be an actual date, and in the past. "Actual date" means no Feb 31, for example.
   * </ul>
   * NOTE: we cannot satisfy the "Actual date" criteria here since we are only dealing with Date 
   * instances.
  */
  public void validate() {
    if (StringUtils.isEmpty(user.getFirstName()) && StringUtils.isEmpty(user.getLastName())
        && StringUtils.isEmpty(user.getPhone())) {
      throw new ValidationError("Insufficent data to create user");
    }

    if (user.getFirstName() != null) {
      if (!StringUtils.isAlpha(user.getFirstName())) {
        throw new ValidationError(MessageFormat.format("First name \"{0}\" must contain alpha characters only",
            user.getFirstName()));
      }
    }

    if (user.getLastName() != null) {
      if (!StringUtils.isAlpha(user.getLastName())) {
        throw new ValidationError(MessageFormat.format("Last name \"{0}\" must contain alpha characters only",
            user.getLastName()));
      }
    }

    if (user.getEmail() != null) {
      String emailCaps = user.getEmail().toUpperCase();
      if (!emailCaps.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$")) {
        throw new ValidationError(MessageFormat.format("Invalid email \"{0}\"", user.getEmail()));
      }
    }

    if (user.getPhone() != null) {
      Pattern datePatt = Pattern.compile("^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$");
      Matcher m = datePatt.matcher(user.getPhone());
      if (m.matches()) {
        //time to normalize the phone number like so 555-555-5555
        //kinda strange that a validator modifies the value, but seems the most efficient spot to do it
        user.setPhone(m.group(1) + "-" + m.group(2) + "-" + m.group(3));
      } else {
        throw new ValidationError(MessageFormat.format("Invalid phone \"{0}\"", user.getPhone()));
      }
    }

    if (user.getDob() != null) {
      Calendar mostRecentLegalBirthDate = Calendar.getInstance();
      mostRecentLegalBirthDate.add(Calendar.YEAR, -18);

      if (user.getDob().after(mostRecentLegalBirthDate.getTime())) {
        throw new ValidationError(MessageFormat.format("Birth date \"{0}\" if after legal limit", user.getDob()));
      }
    }
  }

}
