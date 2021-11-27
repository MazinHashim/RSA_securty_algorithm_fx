package application;


import java.math.BigInteger;

import java.util.Random;



public class RSA

{

    private BigInteger p;

    private BigInteger q;

    private BigInteger N;

    private BigInteger Olier;

    private BigInteger e;

    private BigInteger d;

    private int        bitlength = 1024;

    private Random     r;



    public RSA()

    {

        r = new Random();

        p = BigInteger.probablePrime(bitlength, r);
//        BigInteger big = new BigInteger("1");
//        big.

        q = BigInteger.probablePrime(bitlength, r);

        N = p.multiply(q);

        Olier = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.probablePrime(bitlength / 2, r);

        while (Olier.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(Olier) < 0)

        {

            e.add(BigInteger.ONE);

        }

        d = e.modInverse(Olier);

    }

    public static String bytesToString(byte[] encrypted)

    {

        String test = "";

        for (byte b : encrypted)

        {

            test += Byte.toString(b);

        }

        return test;

    }



    // Encrypt message

    public byte[] encrypt(byte[] message)

    {

        return (new BigInteger(message)).modPow(e, N).toByteArray(); //message^e mod n

    }



    // Decrypt message

    public byte[] decrypt(byte[] message)
    {

        return (new BigInteger(message)).modPow(d, N).toByteArray(); //message^d mod n

    }

}