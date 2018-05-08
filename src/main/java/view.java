import com.kennycason.kumo.*;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.palette.LinearGradientColorPalette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class view implements ActionListener {
    private JTextField textField = null;
    private JTextField textField2 = null;
    private JButton button1 = null;
    private JButton button2 = null;
    private JButton button3 = null;
    private JFrame frame = null;
    private JComboBox typeCom = null;
    public String input;
    public String output;
    public String type;

    public view(){
        frame = new JFrame("Test");
        button1 = new  JButton("open");
        button2 = new  JButton("wordcloud");
        button3 = new  JButton("out");
        textField = new JTextField("",20);
        textField2 = new JTextField("",20);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));

        typeCom = new JComboBox(new String[]{
                "circular","rectangle","whale","cloud","Linear Color Gradients"
        });
        typeCom.setBounds(100,200,400,30);
        typeCom.setBackground(Color.white);

        frame.add(textField);
        frame.add(button1);
        frame.add(textField2);
        frame.add(button3);
        frame.add(typeCom);
        frame.add(button2);
        button1.addActionListener(this);
        button3.addActionListener(this);
        button2.addActionListener(this);
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==button1){
        JFileChooser jfc=new JFileChooser();
        textField.setText("");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "Select");
        File file=jfc.getSelectedFile();
        if(file.isDirectory()){
            textField.setText(file.getAbsolutePath());
            System.out.println("Directory:"+file.getAbsolutePath());
        }else if(file.isFile()){
            textField.setText(file.getAbsolutePath());
            System.out.println("File:"+file.getAbsolutePath());
        }
        System.out.println(jfc.getSelectedFile().getName());
    }
    if(e.getSource() ==button3){
        JFileChooser jfc=new JFileChooser();
        textField2.setText("");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "Select");
        File file=jfc.getSelectedFile();
        if(file.isDirectory()){
            textField2.setText(file.getAbsolutePath());
            System.out.println("Directory:"+file.getAbsolutePath());
        }else if(file.isFile()){
            textField2.setText(file.getAbsolutePath());
            System.out.println("File:"+file.getAbsolutePath());
        }
        System.out.println(jfc.getSelectedFile().getName());
    }
    if(e.getSource() ==button2){
        input = textField.getText();
        output = textField2.getText();
        type = typeCom.getSelectedItem().toString();
        try {
            functionGetWordCloud(input,output,type);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
    }
    public static final String[] imageBackground  = new String[]{
            "cat.bmp","cloud_bg.bmp","cloud_fg.bmp","earth.bmp",
            "haskell_1.bmp","haskell_2.bmp", "pho_full.bmp",
            "dragon.png","sm-logo.png","whale.png"
    };
    public static void functionGetWordCloud(String inputFile,String outputFile,String type) throws IOException{
        //     restore words from input file
        List<WordFrequency> wordSet=null;
        // define wordCloud
        Dimension dimension = new Dimension(600, 600);
        PolarWordCloud wordCloud = null;
        Random random = new Random();
        if (type.equals("circular")){
            wordSet = getWordSet(inputFile);
            wordCloud = new PolarWordCloud(dimension,CollisionMode.PIXEL_PERFECT, PolarBlendMode.BLUR);
            wordCloud.setBackground(new CircleBackground(300));
            wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
            wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
            wordCloud.build(wordSet);
            wordCloud.writeToFile(outputFile);
        }else if (type.equals("rectangle")){
            wordSet = getWordSet(inputFile);
            wordCloud = new PolarWordCloud(dimension,CollisionMode.PIXEL_PERFECT, PolarBlendMode.BLUR);
            wordCloud.setBackground(new RectangleBackground(dimension));
            wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
            wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
            wordCloud.build(wordSet);
            wordCloud.writeToFile(outputFile);
        }else if (type.equals("whale")){
            final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
            frequencyAnalyzer.setWordFrequenciesToReturn(300);
            frequencyAnalyzer.setMinWordLength(4);
            final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(inputFile);
            final Dimension dimension2 = new Dimension(500, 312);
            final WordCloud wordCloud2 = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
            wordCloud2.setPadding(2);
            wordCloud2.setBackground(new PixelBoundryBackground("backgrounds/whale_small.png"));
            wordCloud2.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
            wordCloud2.setFontScalar(new LinearFontScalar(10, 40));
            wordCloud2.build(wordFrequencies);
            wordCloud2.writeToFile(outputFile+"/whale.png");
            }

        else if (type.equals("cloud")){
            final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
            frequencyAnalyzer.setWordFrequenciesToReturn(300);
            frequencyAnalyzer.setMinWordLength(4);
            final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(inputFile);
            final WordCloud wordCloud3 = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
            wordCloud3.setPadding(2);
            wordCloud3.setBackground(new PixelBoundryBackground("backgrounds/cloud_bg.bmp"));
            wordCloud3.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
            wordCloud3.setFontScalar(new LinearFontScalar(10, 40));
            wordCloud3.build(wordFrequencies);
            wordCloud3.writeToFile(outputFile+"/cloud.png");
            }
        else {
            final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
            frequencyAnalyzer.setWordFrequenciesToReturn(500);
            frequencyAnalyzer.setMinWordLength(4);
            final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(inputFile);
            final Dimension dimension1 = new Dimension(600, 600);
            final WordCloud wordCloud1 = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
            wordCloud1.setPadding(2);
            wordCloud1.setBackground(new CircleBackground(300));
            wordCloud1.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
            wordCloud1.setFontScalar( new SqrtFontScalar(10, 40));
            wordCloud1.build(wordFrequencies);
            wordCloud1.writeToFile(outputFile);
        }
    }
    public static void haskellWordCloud(String[] inputFile,String outputFile) throws IOException{
        List<WordFrequency> wordSetOne=null;
        List<WordFrequency> wordSetTwo=null;
        wordSetOne = getWordSet(inputFile[0]);
        wordSetTwo = getWordSet(inputFile[1]);

        //get two wordCloud object
        final Dimension dimension = new Dimension(600, 424);
        final LayeredWordCloud layeredWordCloud = new LayeredWordCloud(2, dimension, CollisionMode.PIXEL_PERFECT);

        layeredWordCloud.setPadding(0, 1);
        layeredWordCloud.setPadding(1, 1);

        layeredWordCloud.setKumoFont(0, new KumoFont("LICENSE PLATE", FontWeight.BOLD));
        layeredWordCloud.setKumoFont(1, new KumoFont("Comic Sans MS", FontWeight.BOLD));

        layeredWordCloud.setBackground(0, new PixelBoundryBackground("backgrounds/haskell_1.bmp"));
        layeredWordCloud.setBackground(1, new PixelBoundryBackground("backgrounds/haskell_2.bmp"));

        layeredWordCloud.setColorPalette(0, new ColorPalette(new Color(0xABEDFF), new Color(0x82E4FF), new Color(0x55D6FA)));
        layeredWordCloud.setColorPalette(1, new ColorPalette(new Color(0xFFFFFF), new Color(0xDCDDDE), new Color(0xCCCCCC)));

        layeredWordCloud.setFontScalar(0, new SqrtFontScalar(10, 40));
        layeredWordCloud.setFontScalar(1, new SqrtFontScalar(10, 40));

        layeredWordCloud.build(0, wordSetOne);
        layeredWordCloud.build(1, wordSetTwo);
        layeredWordCloud.writeToFile(outputFile);
    }

    public static   List<WordFrequency> getWordSet(String file) throws IOException {
        List<WordFrequency> wordSet = new ArrayList<>();
        String reg="[\\[\\]\\{\\}\\\\.?!:;,\"'*&$#%@()\\s\\+\\-]";
        Pattern p = Pattern.compile(reg);
        HashMap<String,Integer> wordCount = new HashMap<>();

        //        read english text file and split word
        FileInputStream fileInputStream = new FileInputStream(new File(file));
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String text = "";
        while ((text=bufferedReader.readLine())!=null){
            String[] wordArray = p.split(text);
            for (int i=0;i<wordArray.length;i++) {
                String word = wordArray[i];
                word = word.trim();
                word = word.toLowerCase();
                if (wordCount.get(word) != null) {
                    int count = wordCount.get(word);
                    count++;
                    wordCount.put(word, count);
                } else {
                    wordCount.put(word, 1);
                }
            }
        }
        //       get word set with frequency
        Iterator iterator = wordCount.entrySet().iterator();
        Map.Entry entry = null;
        while (iterator.hasNext()){
            entry = (Map.Entry)iterator.next();
            String word = entry.getKey().toString();
            int frequency = Integer.parseInt(entry.getValue() + "");
            WordFrequency wordFrequency = new WordFrequency(word,frequency);
            wordSet.add(wordFrequency);
        }
        return wordSet;
    }
    public static void main(String[] args) {
        new view();

    }
}