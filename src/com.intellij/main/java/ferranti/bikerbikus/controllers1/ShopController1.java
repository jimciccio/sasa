package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.BiciclettaNoleggio;
import ferranti.bikerbikus.models.BiciclettaVendita;
import ferranti.bikerbikus.queries.ShopQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShopController1 {

    protected ShopController1(){}

    protected static final List<BiciclettaVendita> bicicletteVendita = new ArrayList<>();
    protected static final List<BiciclettaNoleggio> bicicletteNoleggio = new ArrayList<>();


    public static void loadBicicletteComprabili() {
        bicicletteVendita.clear();
        bicicletteVendita.addAll(ShopQuery.findBicicletteComprabili());
    }

    public static void loadBicicletteNoleggiabili() {
        bicicletteNoleggio.clear();
        bicicletteNoleggio.addAll(ShopQuery.findBicicletteNoleggiabili());
    }

    public static List loadBiciclette(String id) {
        switch (id) {
            case "tabNuove": {
                bicicletteVendita.clear();
                bicicletteVendita.addAll(ShopQuery.findBicicletteComprabili());
                return bicicletteVendita;
            }
            case "tabNoleggiate": {
                bicicletteNoleggio.clear();
                bicicletteNoleggio.addAll(ShopQuery.findBicicletteNoleggiabili());
                return bicicletteNoleggio;
            }

            default:
                throw new IllegalArgumentException("Unexpected value: " + id);
        }
    }

    public static boolean buyBicicletta(int item){

                if(ShopQuery.insertBiciclettaComprata(bicicletteVendita.get(item), UserData.getInstance().getUser().getId())){
                    if(ShopQuery.updateBiciclettaComprabile(bicicletteVendita.get(item).getId())){
                        loadBicicletteComprabili();
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
    }

    public static boolean rentBicicletta(int item, LocalDate date){

        if( ShopQuery.insertBiciclettaNoleggiata(bicicletteNoleggio.get(item), UserData.getInstance().getUser().getId(), finalPrice(date, bicicletteNoleggio.get(item).getPrezzo()), date)){
            if(ShopQuery.updateBiciclettaNoleggiabile(bicicletteNoleggio.get(item).getId())){
                loadBicicletteNoleggiabili();
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public static int finalPrice(LocalDate endDate, int priceDay){
        int finalPrice;
        long daysElapsed = java.time.temporal.ChronoUnit.DAYS.between( LocalDate.now() , endDate ) ;
        int i = (int) daysElapsed + 1;
        finalPrice=i*priceDay;
        return finalPrice;
    }
}
