import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class HotDrinkMashine{
    private static HotDrinkMashine instanse;

    public static HotDrinkMashine getInstanse(){
        if(instanse==null){
            return new HotDrinkMashine();
        }
        return instanse;
    }

    private static Map<Drink,Integer> drinkList = new HashMap<Drink,Integer>();    
    private static Map<Integer,Drink> drinks = new HashMap<Integer,Drink>();
    private static Scanner scan = new Scanner(System.in);
    private static String drinkName;
    private static Map<Integer,String> mainMenu = new HashMap<Integer,String>();
    private static Map<Integer,String> namesMenu = new HashMap<Integer,String>();
    private static Map<Integer,Drink> valuesMenu = new HashMap<Integer,Drink>();

    HotDrinkMashine(){
        // this.welcome();        
        // this.add_product();
        // this.run();
        
    }

    public static void getStart(){
        HotDrinkMashine.welcome();        
        HotDrinkMashine.add_product();
        HotDrinkMashine.run();
        
    }

    public static void add_product(){
        HotDrinkMashine.drinkList.put(new Drink("кофе","Эспрессо",100,50,80), 10);
        HotDrinkMashine.drinkList.put(new Drink("кофе","Эспрессо",150,80,80), 10);
        
        HotDrinkMashine.drinkList.put(new Drink("кофе","Капучино",200,200,80), 10);
        HotDrinkMashine.drinkList.put(new Drink("кофе","Капучино",250,260,80), 10);
        
        HotDrinkMashine.drinkList.put(new Drink("кофе","Латте Мокиато",120,150,80), 10);
        HotDrinkMashine.drinkList.put(new Drink("кофе","Латте Мокиато",250,200,80), 10);

        HotDrinkMashine.drinkList.put(new Drink("чай","черный",100,100,90), 2);
        HotDrinkMashine.drinkList.put(new Drink("чай","черный",150,150,90), 2);
        
        
        HotDrinkMashine.drinkList.put(new Drink("чай","Пуэр",100,200,90), 5);
        HotDrinkMashine.drinkList.put(new Drink("чай","Пуэр",140,280,90), 5);
        
        HotDrinkMashine.drinkList.put(new Drink("чай","Каркадэ",100,200,90), 5);
        HotDrinkMashine.drinkList.put(new Drink("чай","Каркадэ",150,260,90), 5);

        HotDrinkMashine.drinkList.put(new Drink("горячий шоколад","Шоколад",100,100,90), 6);
        HotDrinkMashine.drinkList.put(new Drink("горячий шоколад","Шоколад",200,200,90), 6);
    }

    private static Map<Integer,String> create_menu(String name,Map<Drink,Integer> lst){
        Map<Integer,String> menu = new HashMap<Integer,String>();
        Set<String> tmp = new HashSet<String>();
        int cnt=1;
        if(name.isEmpty())
        {
            for(var item:lst.entrySet())
            {   
                if(item.getValue()>0){
                    tmp.add(item.getKey().get_type());
                }
            }
        }
        else{
            for(var item:lst.entrySet()){
                if(item.getKey().get_type().compareToIgnoreCase(name)==0 && item.getValue()>0){
                    tmp.add(item.getKey().get_name());
                }
            }
        }

        for(String item:tmp)
        {
            menu.put(cnt, item);
            cnt++;
        }

        return menu;

    }

    private static void show_menu(String info,Map<Integer,String> menu){

        System.out.println();
        System.out.println(info);
        System.out.println();

        for(var item:menu.entrySet())
        {        
            System.out.print(item.getKey());
            System.out.print(" - ");
            System.out.println(item.getValue());
        }
        System.out.println();
        
    }

    private static String user_input(String info,Map<Integer,String> lst,String back)
    {
        String res="";
        
        System.out.print(info);
        String input=scan.next();
        
        if(back.compareToIgnoreCase("0")==0 && input.compareToIgnoreCase("0")==0)
        {
            return "-1";
        }
        
        if(lst.containsKey(Integer.parseInt(input)))
        {
            res = lst.get(Integer.parseInt(input));
        }
        return res;
    }

    private static void run(){
        boolean step2=false;
        while(true){
            step2=true;
            HotDrinkMashine.clear();
            mainMenu = (HashMap<Integer,String>)HotDrinkMashine.create_menu("",HotDrinkMashine.drinkList);
            show_menu("***** Меню напитков *****", mainMenu);
            String drinkType = HotDrinkMashine.user_input("Выберите напиток: ",mainMenu,"");
            if(drinkType.compareToIgnoreCase("")!=0){
                while (step2){
                    namesMenu = (HashMap<Integer,String>)HotDrinkMashine.create_menu(drinkType, drinkList);
                    HotDrinkMashine.clear();
                    show_menu("***** Меню напитков-1 *****", namesMenu);
                    String drinkName = HotDrinkMashine.user_input("Выберите напиток (0-вернуться на шаг): ",namesMenu,"0");
                    System.out.println(drinkName);
                    if(drinkName.compareTo("-1")==0)
                    {
                        break;
                    }
                    if(drinkName.compareToIgnoreCase("")!=0){
                        while(true){
                            HotDrinkMashine.clear();
                            valuesMenu = HotDrinkMashine.get_drinks(drinkName, drinkList);
                            show_drinks("***** Меню напитков *****",valuesMenu);
                            Drink dr = get_drink(valuesMenu,"0");
                           
                            if(dr.get_name().compareToIgnoreCase("back")==0){
                                break;
                            }
                            if(dr.get_name().compareToIgnoreCase("prev")!=0){
                                HotDrinkMashine.get_product(dr);
                                step2=false;
                                break;
                            }
                        }
                    }

                }
            }
        }
    }

  
     private static void get_product(Drink drink){
        HotDrinkMashine.clear();
        System.out.println("Идет приготовление напитка");
        for(var item:drinkList.entrySet())
        {
            if(item.getKey().compareTo(drink)==1)
            { 
                drinkList.replace(item.getKey(), drinkList.get(item.getKey()), drinkList.get(item.getKey())-1);
            }
        }

        HotDrinkMashine.printCheck(drink);

        try{
            Thread.sleep(1500);
        }
        catch (InterruptedException ex) {
        }
    }

    private static Drink get_drink(Map<Integer,Drink> lst,String back){
        
        Drink res = new Drink("tmp", "prev", 0, 0, 0);
        System.out.println();
        System.out.print("Выберите напиток (0-вернуться на шаг): ");
        String input=scan.next();
        
        if(input.compareToIgnoreCase(back)==0)
        {   
            System.out.println("!");
            res.set_name("back");
        }
        else{
            if(lst.containsKey(Integer.parseInt(input)))
            {
                res = lst.get(Integer.parseInt(input));
            }
        }
        
        return res;

    }

    private static void show_drinks(String info,Map<Integer,Drink> lst){
        System.out.println();
        System.out.println(info);
        System.out.println();

        System.out.println("   Наименование   объем   температура  цена");
        for(var item:lst.entrySet())
        {
            System.out.print(item.getKey());
            System.out.print(" - ");
            System.out.print(item.getValue().get_name());
            System.out.print("       ");
            System.out.print(item.getValue().get_volume());
            System.out.print("        ");
            System.out.print(item.getValue().get_temperature());
            System.out.print("      ");
            System.out.println(item.getValue().get_cost());
            
        }
    }

    private static Map<Integer,Drink> get_drinks(String drinkName, Map<Drink,Integer> lst){
        Map<Integer,Drink> result = new HashMap<Integer,Drink>();
        int cnt=1;
        
        for(var item:lst.entrySet()){
          
            if(item.getKey().get_name().compareToIgnoreCase(drinkName)==0 && item.getValue()>0){
                result.put(cnt, item.getKey());
                cnt++;
            }
        }
        
        return result;

    }

    private static void printCheck(Drink drink){
        System.out.println("Печать чека.");
        Iterator<String> drinkField = drink;
        while(drinkField.hasNext())
        {
            System.out.println(drinkField.next());
        }
    }

    private static void welcome(){
        // System.out.print("\033[H\033[J");
        System.out.println("Торговый автомат v1.0");
        // this.add_product();
    }

    private static void clear(){
        System.out.print("\033[H\033[J");
    }
  
}