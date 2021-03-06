package phonebase.android.kizema.phonebasetestapp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(indexes = {
        @Index(value = "phoneNumber", unique = true)
})
public class Contact {

    @Id
    public String phoneNumber;

    @NotNull
    public int phoneNumberPrice;

    @NotNull
    public String phoneNumberOwner;

    public double sortValue;


    public String dictionaryWord;

public Contact(@NotNull String phoneNumber, int phoneNumberPrice,
        String phoneNumberOwner, String dictionaryWord, boolean isProccessed) {
    this.phoneNumber = phoneNumber;
    this.phoneNumberPrice = phoneNumberPrice;
    this.phoneNumberOwner = phoneNumberOwner;
    this.dictionaryWord = dictionaryWord;
}

@Generated(hash = 672515148)
public Contact() {
}

@Generated(hash = 328827515)
public Contact(String phoneNumber, int phoneNumberPrice,
        @NotNull String phoneNumberOwner, double sortValue,
        String dictionaryWord) {
    this.phoneNumber = phoneNumber;
    this.phoneNumberPrice = phoneNumberPrice;
    this.phoneNumberOwner = phoneNumberOwner;
    this.sortValue = sortValue;
    this.dictionaryWord = dictionaryWord;
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

public String getDictionaryWord() {
    return this.dictionaryWord;
}

public void setDictionaryWord(String dictionaryWord) {
    this.dictionaryWord = dictionaryWord;
}

public double getSortValue() {
    return this.sortValue;
}

public void setSortValue(double sortValue) {
    this.sortValue = sortValue;
}

}
