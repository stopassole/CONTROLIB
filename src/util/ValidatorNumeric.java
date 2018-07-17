package util;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ValidatorNumeric {

	public static void numericField(final TextField textField) {
		textField.lengthProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					char ch = textField.getText().charAt(oldValue.intValue());
					if (!(ch >= '0' && ch <= '9')) {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
					}
				}
			}
		});
	}
}
