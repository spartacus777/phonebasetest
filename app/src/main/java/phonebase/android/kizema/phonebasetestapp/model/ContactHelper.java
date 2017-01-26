package phonebase.android.kizema.phonebasetestapp.model;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import phonebase.android.kizema.phonebasetestapp.App;

public class ContactHelper {

    public static Contact create(String phone, int price, String owner){
        Contact c = getContactByOwner(owner);
        boolean isNew = false;

        if (c == null){
            isNew = true;
            c = new Contact();
        }

        c.phoneNumber = phone;
        c.phoneNumberPrice = price;
        c.phoneNumberOwner = owner;

        if (isNew) {
            App.getDaoSession().getContactDao().insert(c);
        } else {
            App.getDaoSession().getContactDao().update(c);
        }

        return c;
    }

    public static Contact getContactByOwner(String contactOwner){
        DaoSession daoSession = App.getDaoSession();
        ContactDao confDao = daoSession.getContactDao();

        QueryBuilder<Contact> queryBuilder = confDao.queryBuilder();
        List<Contact> contactlist = queryBuilder.where(ContactDao.Properties.PhoneNumberOwner.eq(contactOwner)).list();

        if (contactlist == null || contactlist.size() == 0) {
            return null;
        } else {
            return contactlist.get(0);
        }
    }

    public static List<Contact> getAll(){
        DaoSession daoSession = App.getDaoSession();
        ContactDao confDao = daoSession.getContactDao();

        QueryBuilder<Contact> queryBuilder = confDao.queryBuilder();
        List<Contact> contactlist = queryBuilder.list();

        return contactlist;
    }
}
