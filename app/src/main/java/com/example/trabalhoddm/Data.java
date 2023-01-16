package com.example.trabalhoddm;

import java.util.ArrayList;
import java.util.List;


public class Data {
    public static List<Moeda> getMoedas(){
        List<Moeda> listaMoedas = new ArrayList<>();

        Moeda bitcoin = new Moeda();
        bitcoin.setName("bitcoin");
        bitcoin.setImage(R.drawable.bitcoin);
        listaMoedas.add(bitcoin);

        Moeda BNB = new Moeda();
        BNB.setName("binancecoin");
        BNB.setImage(R.drawable.bnb);
        listaMoedas.add(BNB);


        Moeda ethereum = new Moeda();
        ethereum.setName("ethereum");
        ethereum.setImage(R.drawable.ethereum);
        listaMoedas.add(ethereum);

        Moeda XRP = new Moeda();
        XRP.setName("ripple");
        XRP.setImage(R.drawable.xrp);
        listaMoedas.add(XRP);

        Moeda ADA = new Moeda();
        ADA.setName("cardano");
        ADA.setImage(R.drawable.ada);
        listaMoedas.add(ADA);

        Moeda DOGE = new Moeda();
        DOGE.setName("dogecoin");
        DOGE.setImage(R.drawable.dogecoin);
        listaMoedas.add(DOGE);

        Moeda MATIC = new Moeda();
        MATIC.setName("matic-network");
        MATIC.setImage(R.drawable.polygon);
        listaMoedas.add(MATIC);

        Moeda SOL  = new Moeda();
        SOL.setName("solana");
        SOL.setImage(R.drawable.solana);
        listaMoedas.add(SOL);

        Moeda  RVN   = new Moeda();
        RVN .setName("ravencoin");
        RVN .setImage(R.drawable.ravencoin);
        listaMoedas.add( RVN );

        Moeda ETC = new Moeda();
        ETC .setName("ethereum-classic");
        ETC .setImage(R.drawable.ethereum_classic);
        listaMoedas.add(ETC);

        Moeda XCH = new Moeda();
        XCH.setName("chia");
        XCH.setImage(R.drawable.chia );
        listaMoedas.add(XCH);




        return listaMoedas;
    }


}
