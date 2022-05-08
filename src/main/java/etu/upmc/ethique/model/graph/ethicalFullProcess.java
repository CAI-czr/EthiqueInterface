package etu.upmc.ethique.model.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.File;
import java.util.*;


public class ethicalFullProcess {
    
    public static void main(String[] args){
        if (args.length<2)
        System.out.println("pas de fichier de config\n");
        String fic=args[1];
        try
        {
            String encoding = "UTF-8";
            File file = new File(fic);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int mode=-1;
                String modelab[]={"[clingo]","[action theory]","[context]","[causal theory]","[ethical spec]","[ethical theories]", "[trace]", "[causal trace]","[results]","[output]"};
                String repclingo;
                String[] action;
                String[] ctx;
                String[] causal;
                String[] ethSpec,ethics,resPred;
                String tr,ctr,output;
                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    if (lineTxt.charAt(0)=='['){
                        mode=modeLab.index(lineTxt);
                    }
                    else if ( mode>=0){
                        if (mode==modeLab.index("[clingo]"))
                            repclingo=lineTxt;
                        else if (mode==modeLab.index("[action theory]"))
                            action[action.length]=lineTxt;
                        else if (mode==modeLab.index("[context]"))
                            ctx[ctx.length]=lineTxt;
                        else if(mode==modeLab.index("[causal theory]"))
                            causal[causal.length]=lineTxt;
                        else if(mode==modeLab.index("[ethical spec]"))
                            ethSpec[ethSpec.length]=lineTxt;
                        else if (mode==modeLab.index("[ethical theories]"))
                            ethics[ethics.length]=lineTxt;
                        else if (mode==modeLab.index("[trace]"))
                            tr=lineTxt;
                        else if (mode==modeLab.index("[causal trace]"))
                            ctr=lineTxt;
                        else if (mode==modeLab.index("[results]"))
                            resPred[resPred.length]=lineTxt;
                        else if (mode==modeLab.index("[output]"))
                            output=lineTxt;
                    }
                }
                bufferedReader.close();
                read.close();
            }
            else
            {
                System.out.println("The specified document could not be found");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error reading file contents");
            e.printStackTrace();
        }

        process(repclingo,action,ctx,causal,ethSpec,ethics,tr,ctr,resPred,output); 
    }
    
    public void process(String clingo,String[] action,String[] ctx,String[] causal,String[] ethSpec,String[] ethics,String tr,String ctr,String[] resPred,String output){
        String trRoot;
        if( tr!=""){
            trRoot=tr[0:tr.rfind('.')];}
        else{ trRoot="tmpTrace";}
        
        solve(clingo,action[0]+" "+ctx[0],trRoot+'.tmp');
        readAndWriteAS(trRoot+'.tmp',tr);
        String tempOutput;
        if (output!=""){
            tempOutput=output[0:output.rfind('.')]+".tmp";
        }
        if (resPred!=""){
            String s="#show ";
            for (String pred : resPred){
                s+=pred+" .\n";
            }
            try {
                File file = new File("filter.tmp");
             
                // if file doesnt exists, then create it
                if (!file.exists()) {
                 file.createNewFile();
                }
             
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(s);
                bw.close();
                } catch (IOException e) {
                e.printStackTrace();
                }
               solve(clingo,ctr+" filter.tmp "+ethics[0]+" "+ctx[0]+" "+ethSpec[0],tempOutput);
        
            
        }
        else{
            solve(clingo,ctr+" "+ethics[0]+" "+ctx[0]+" "+ethSpec[0],tempOutput);
        }
        if (output!=""){
            readAndWriteAS(tempOutput,output);
        }
    }

    public void solve(String repclingo,String theories,String output){
        String cmd=repclingo+" -V0 0 " +theories;
        if (output.length()>0){
            cmd+=" > " +output;}
        System.out.println("Executing : "+cmd);
        Runtime rt=Runtime.getRuntime();
        rt.exec(cmd);
        System.out.println(".... done.\n");
    }

    public void readAndWriteAS(String outputClingo,String newfile){
        System.out.println("translating "+outputClingo+" into "+newfile+"\n");
        File file = new File(outputClingo);
        Long filelength = file.length(); // 获取文件长度
        byte[] filecontent = new byte[filelength.intValue()];
        try
        {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        String[] fileContentArr = new String(filecontent).split("\r\n");
        String facts=fileContentArr[0].split();
        try {
            file = new File(newfile);
         
            // if file doesnt exists, then create it
            if (!file.exists()) {
             file.createNewFile();
            }
         
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            Collection list = java.util.Arrays.asList(facts);
            for (String fact:list){
                bw.write(fact+".\n");
            }
            bw.close();
            } catch (IOException e) {
            e.printStackTrace();
           }
    }
}


