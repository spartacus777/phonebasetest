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

    public double sortValue;

    @Id
    public String phoneNumberOwner;

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

@Generated(hash = 173475996)
public Contact(@NotNull String phoneNumber, int phoneNumberPrice,
        double sortValue, String phoneNumberOwner, String dictionaryWord) {
    this.phoneNumber = phoneNumber;
    this.phoneNumberPrice = phoneNumberPrice;
    this.sortValue = sortValue;
    this.phoneNumberOwner = phoneNumberOwner;
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
