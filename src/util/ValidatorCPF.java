package util;

import br.com.caelum.stella.validation.CPFValidator;
import javafx.scene.control.TextField;

public class ValidatorCPF {

	private TextField textfield;

	public ValidatorCPF(TextField textfield) {
		this.textfield = textfield;
		listeners();
	}

	private String numeroCPF(String cpfComMascara) {
		return cpfComMascara.replace(".", "").replace("-", "");
	}

	private String formataCpf(String s) {
		int i = s.length();
		String digito = String.valueOf(s.charAt(i - 1));
		if (i > 0) {
			if ("0123456789".contains(digito)) {
				if (i == 3 || i == 7) {
					s = s + ".";
				} else if (i == 11) {
					s = s + "-";
				} else if (i == 4 || i == 8) {
					s = s.substring(0, i - 1) + "." + digito;
				} else if (i == 12) {
					s = s.substring(0, i - 1) + "-" + digito;
				} else if (i > 14) {
					return s.substring(0, 14);
				}
			} else {
				if (((i == 4 || i == 8) && (s.charAt(i - 1) == '.')) || ((s.charAt(i - 1) == '-') && (i == 12))) {
					return s;
				} else {
					return s.substring(0, i - 1);
				}
			}
		}
		return s;
	}

	public boolean isValidCPF(String cpf) {
		cpf = cpf.replaceAll("[^0-9]", "");
		CPFValidator cpfValidator = new CPFValidator();
		try {
			cpfValidator.assertValid(cpf);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void listeners() {
		this.textfield.textProperty().addListener((obs, oldv, newv) -> {
			if (newv.length() > oldv.length()) {
				this.textfield.setText(this.formataCpf(newv));
			}
		});
	}

	public String getCpfComMascara() {
		return this.textfield.getText();
	}

	public String getCpfSemMascara() {
		return numeroCPF(textfield.getText());
	}
}
