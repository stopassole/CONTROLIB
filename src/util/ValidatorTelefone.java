package util;

import javafx.scene.control.*;

public class ValidatorTelefone {
    
    private TextField textfield;
    
    public ValidatorTelefone(TextField textfield){
        this.textfield = textfield;
        listeners();
    }
    
    private String numeroTelFixo(String telFixoComMascara){
        return telFixoComMascara.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
    }
    private String formataTelFixo(String s) {
        int i = s.length();
        String digito = String.valueOf(s.charAt(i - 1));

        if (i > 0) {
            if ("0123456789".contains(String.valueOf(s.charAt(i - 1)))) {
                if( i == 1 ){
                    s = "(" + s;
                } else if (i == 3) {
                    s += ") ";
                } else if (i == 9) {
                    s += "-";
                } else if(i == 4){
                    s = s.substring(0, i-1) + ")" + digito;
                } else if(i == 5){
                    s = s.substring(0, i-1) + " " + digito;
                } else if(i == 10){
                    s = s.substring(0, i-1) + "-" + digito;
                } else if (i > 14) {
                    return s.substring(0, 14);
                }
            } else {
                if (((i == 1) && (s.charAt(i - 1) == '(')) || ((i == 4) && (s.charAt(i - 1) == ')')) || ((i == 5) && (s.charAt(i - 1) == ' ')) || ((i == 10) && (s.charAt(i - 1) == '-'))) {
                    return s;
                } else {
                    return s.substring(0, i - 1);
                }
            }
        }
        return s;
    }
    private void listeners() {
        this.textfield.textProperty().addListener((obs, oldv, newv) -> {
            if (newv.length() > oldv.length()) {
                this.textfield.setText(this.formataTelFixo(newv));
            }else{
                if((newv.length() == 1) && (oldv.length() - newv.length() > 2)){
                    this.textfield.setText("(" + newv);
                }
            }
        });
    }
    
    public String getTelFixoComMascara(){
        return this.textfield.getText();
    }
    
    public String getTelFixoSemMascara(){
        return numeroTelFixo(textfield.getText());
    }
}