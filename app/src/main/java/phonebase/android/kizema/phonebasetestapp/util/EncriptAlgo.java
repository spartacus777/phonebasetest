package phonebase.android.kizema.phonebasetestapp.util;

import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncriptAlgo {

    private static final byte[] f2745a;
    static final byte[] f2457a;
    private static byte[] f2458b;
    private static byte[] f2459c;

    static {
        int i;
        int i2 = 0;
        f2457a = "\r\n".getBytes();
        f2458b = new byte[255];
        f2459c = new byte[64];
        for (i = 0; i < 255; i++) {
            f2458b[i] = (byte) -1;
        }
        for (i = 90; i >= 65; i--) {
            f2458b[i] = (byte) (i - 65);
        }
        for (i = 122; i >= 97; i--) {
            f2458b[i] = (byte) ((i - 97) + 26);
        }
        for (i = 57; i >= 48; i--) {
            f2458b[i] = (byte) ((i - 48) + 52);
        }
        f2458b[43] = (byte) 62;
        f2458b[47] = (byte) 63;
        for (i = 0; i <= 25; i++) {
            f2459c[i] = (byte) (i + 65);
        }
        int i3 = 26;
        i = 0;
        while (i3 <= 51) {
            f2459c[i3] = (byte) (i + 97);
            i3++;
            i++;
        }
        i = 52;
        while (i <= 61) {
            f2459c[i] = (byte) (i2 + 48);
            i++;
            i2++;
        }
        f2459c[62] = (byte) 43;
        f2459c[63] = (byte) 47;
    }

    static {
        f2745a = new byte[]{(byte) 22, (byte) 0, (byte) 5, (byte) 102, (byte) 116, (byte) 102, (byte) 107, (byte) 113, (byte) 0, (byte) 101, (byte) 4, (byte) 21, (byte) 17, (byte) 7, (byte) 115, (byte) 99};
    }

    /**
     *
     * @param str decode string from remote stolen apk's server
     * @return decoded string
     */
    public static String decode(String str) {
        byte[] bArr = null;
        try {
            Key secretKeySpec = new SecretKeySpec(m5504a(f2745a, "SECRET".getBytes()), "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
            instance.init(2, secretKeySpec);
            bArr = instance.doFinal(m5022a(str.getBytes()));
        } catch (Exception e) {
            Log.d("d", e.toString());
        }
        return new String(bArr);
    }

    private static boolean m5021a(byte b) {
        return b == 61 || f2458b[b] != -1;
    }

    private static byte[] m5023b(byte[] bArr) {
        byte[] obj = new byte[bArr.length];
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (m5021a(bArr[i2])) {
                int i3 = i + 1;
                obj[i] = bArr[i2];
                i = i3;
            }
        }
        byte[] obj2 = new byte[i];
        System.arraycopy(obj, 0, obj2, 0, i);
        return obj2;
    }

    private static byte[] m5022a(byte[] bArr) {
        int i = 0;
        byte[] b = m5023b(bArr);
        if (b.length == 0) {
            return new byte[0];
        }
        int length = b.length / 4;
        int length2 = b.length;
        while (b[length2 - 1] == (byte) 61) {
            length2--;
            if (length2 == 0) {
                return new byte[0];
            }
        }
        byte[] bArr2 = new byte[(length2 - length)];
        int i2 = 0;
        while (i < length) {
            int i3 = i * 4;
            byte b2 = b[i3 + 2];
            byte b3 = b[i3 + 3];
            byte b4 = f2458b[b[i3]];
            byte b5 = f2458b[b[i3 + 1]];
            if (b2 != (byte) 61 && b3 != (byte) 61) {
                b2 = f2458b[b2];
                b3 = f2458b[b3];
                bArr2[i2] = (byte) ((b4 << 2) | (b5 >> 4));
                bArr2[i2 + 1] = (byte) (((b5 & 15) << 4) | ((b2 >> 2) & 15));
                bArr2[i2 + 2] = (byte) ((b2 << 6) | b3);
            } else if (b2 == (byte) 61) {
                bArr2[i2] = (byte) ((b5 >> 4) | (b4 << 2));
            } else if (b3 == (byte) 61) {
                b2 = f2458b[b2];
                bArr2[i2] = (byte) ((b4 << 2) | (b5 >> 4));
                bArr2[i2 + 1] = (byte) (((b5 & 15) << 4) | ((b2 >> 2) & 15));
            }
            i2 += 3;
            i++;
        }
        return bArr2;
    }

    private static byte[] m5504a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length];
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr3[i2] = (byte) (bArr[i2] ^ bArr2[i]);
            i++;
            if (i >= bArr2.length) {
                i = 0;
            }
        }
        return bArr3;
    }
}
