package phonebase.android.kizema.phonebasetestapp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(indexes = {
        @Index(value = "phoneNumberOwner", unique = true)
})
public class Contact {

    @NotNull
    public String phoneNumber;

    @NotNull
    public int phoneNumberPrice;

    @Id
    public String phoneNumberOwner;

@Generated(hash = 885000141)
public Contact(@NotNull String phoneNumber, int phoneNumberPrice,
        String phoneNumberOwner) {
    this.phoneNumber = phoneNumber;
    this.phoneNumberPrice = phoneNumberPrice;
    this.phoneNumberOwner = phoneNumberOwner;
}

@Generated(hash = 672515148)
public Contact() {
}

public String getPhoneNumber() {
    return this.phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
}

public int getPhoneNumberPrice() {
    return this.phoneNumberPrice;
}

public void setPhoneNumberPrice(int phoneNumberPrice) {
    this.phoneNumberPrice = phoneNumberPrice;
}

public String getPhoneNumberOwner() {
    return this.phoneNumberOwner;
}

public void setPhoneNumberOwner(String phoneNumberOwner) {
    this.phoneNumberOwner = phoneNumberOwner;
}
}
