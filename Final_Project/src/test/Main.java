import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String[] options = {"Student", "Admin"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose Mode", "College Admission",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (choice == 0) new StudentForm();
        else new AdminPanel();
    }
}
