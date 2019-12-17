import java.io.*;
import java.util.Scanner;
class SetSamples{
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        File file = new File("samples1.txt");
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);
        PrintWriter pr = new PrintWriter(br);
        String str;
        int num;
        String output;
        String d[]={"NAME","ACCELERATION","SPRINT SPEED","POSITIONING","FINISHING","SHOT POWER","LONG SHOTS","VOLLEYS","PENALTIES","VISION","CROSSING","FREE-KICKS","SHORT PASS","LONG PASS","CURVE","AGILITY","BALANCE","REACTIONS","BALL CONTROL","DRIBBLING","COMPOSURE","INTERCEPTIONS","HEADING","MARKING","STAND TACKLE","SLIDE TACKLE","JUMPING","STAMINA","STRENGTH","AGGRESSION","GK DIVING","GK HANDLING","GK KICKING","GK REFLEXES","GK POSITIONING"};
        while(true){
            System.out.println("Sample Details");
            System.out.println(d[0]);
            str = sc.nextLine();
            output = str;
            for(int i=1;i<d.length;i++){
                System.out.println(d[i]);
                num = sc.nextInt();
                output = output + "," + num;
            }
            pr.println(output);
            System.out.println("Continue?(y/n)");
            str = sc.next();
            sc.nextLine();
            if(str.charAt(0)=='n' || str.charAt(0)=='N')
                break;
        }
        pr.close();
        br.close();
        fr.close();
    }
}