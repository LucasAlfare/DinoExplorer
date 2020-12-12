package com.explorer.main.gui;

import com.dino.main.Dino;
import com.jrelativelayout.main.JRelativeLayout;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;

import static com.jrelativelayout.main.JRelativeLayout.*;

public class Gui extends JFrame {

    private JTextField stateInput, sequenceInput;
    private JButton applySequence, getSolution, getScramble;

    private Dino dino;

    public Gui() {
        init();

        getSolution.addActionListener(e -> {
            String[] state = stateInput.getText().split(" ");
            byte[] statePieces = new byte[state.length];
            for (int i = 0; i < statePieces.length; i++) statePieces[i] = Byte.parseByte(state[i]);
            dino.setStatePermutation(statePieces);

            if (!isSequenceInputEmpty()) dino.applySequence(sequenceInput.getText().trim());

            ArrayList<Byte> solution = dino.getSolution();
            String result = "A solução para o estado " + Arrays.toString(dino.getStatePermutation()) + " é:\n\n";
            result += solution.toString().replace("[", "").replace("]", "") + " [" + solution.size() + "]";
            Result r = new Result();
            r.getTextArea().setText(result);
            r.setVisible(true);
        });
    }

    private boolean isSequenceInputEmpty() {
        return sequenceInput.getText().contains("x") || sequenceInput.getText().isEmpty();
    }

    private void init() {
        dino = new Dino();
        dino.getSolution(); // forces to init the solver

        setSize(400, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new JRelativeLayout());

        final JLabel a = new JLabel("sequência");
        a.setToolTipText("(opcional)");

        final JLabel b = new JLabel("estado:");

        stateInput = new JTextField("0 1 2 3 4 5 6 7 8 9 10");
        stateInput.setToolTipText("digite apenas números (0 a 10, sendo cada um respectivo a uma peça) e use espaços para separa-los.");

        sequenceInput = new JTextField("1 -2 4 3               ");
        sequenceInput.setToolTipText("pré-sequência para ser aplicada ao estado do outro campo de texto (opcional)");

        applySequence = new JButton("aplicar sequência");
        applySequence.setToolTipText("aplica a sequência contida no campo de texto ao estado.");

        getSolution = new JButton("obter solução");
        getSolution.setToolTipText("mostra a sequência que resolve o estado descrito no campo de texto.");

        getScramble = new JButton("obter scramble");
        getScramble.setToolTipText("mostra um embaralhamento que leva ao estado descrito no campo de texto.");

        add(a, constraints(pair(Param.PARENT_TOP, true), pair(Param.PARENT_START, true)));
        add(b, constraints(pair(Param.PARENT_TOP, true), pair(Param.CENTER_HORIZONTAL, true)));

        add(sequenceInput, constraints(pair(Param.BELLOW, a)));
        add(stateInput, constraints(pair(Param.START, b), pair(Param.BELLOW, b)));

        add(getSolution, constraints(pair(Param.PARENT_BOTTOM, true), pair(Param.PARENT_START, true)));
        add(getScramble, constraints(pair(Param.PARENT_BOTTOM, true), pair(Param.PARENT_END, true)));

        setLocationRelativeTo(null);
    }

    public class Result extends JFrame {

        private JTextArea textArea;

        public Result() {
            setSize(400, 150);
            setTitle("Resultados");
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            textArea = new JTextArea();
            add(textArea);
        }

        public JTextArea getTextArea() {
            return textArea;
        }
    }
}
