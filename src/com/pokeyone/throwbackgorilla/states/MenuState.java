package com.pokeyone.throwbackgorilla.states;

import com.pokeyone.throwbackgorilla.*;
import com.pokeyone.throwbackgorilla.Frame;
import com.pokeyone.throwbackgorilla.resources.ResourceHandler;
import com.pokeyone.throwbackgorilla.resources.ResourceImage;
import com.pokeyone.throwbackgorilla.ui.MenuOption;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by pokeyone on 2017-11-27.
 */
public class MenuState extends State {

    public String title;
    public MenuOption[] options;
    private int currentOption = 0;

    private final Font titleFont = new Font("Ariel", Font.BOLD, 48);
    private final Font optionFont = new Font("Ariel", Font.BOLD, 28);

    public MenuState(String title, String stateName, MenuOption... options){
        super(Frame.FRAME_WIDTH, Frame.FRAME_HEIGHT, stateName);

        this.title = title;
        this.options = options;
    }

    public void paint(Graphics g) {
        g.drawImage(((ResourceImage)GameState.resourceHandler.getResource(GameState.RES_BACKGROUND_NAME)).getImage(),
                0, 0, stateWidth, stateHeight, null);

        g.setFont(titleFont);
        g.drawString(title, (stateWidth/2) - 225, 70);

        g.setFont(optionFont);
        g.setColor(Color.WHITE);
        for(int i = 0; i < options.length; i++){
            if(i == currentOption){
                g.setColor(Color.RED);
                g.drawString(options[i].name, 200, i * 30 + 200);
                g.setColor(Color.WHITE);
            }else{
                g.drawString(options[i].name, 200, i * 30 + 200);
            }
        }
    }

    private void performCurrentItem(){
        changeState = options[currentOption].stateName;
    }

    public void tick() {

    }

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                currentOption--;
                if(currentOption < 0){
                    currentOption = options.length -1;
                }
                break;
            case KeyEvent.VK_DOWN:
                currentOption++;
                if(currentOption >= options.length){
                    currentOption = 0;
                }
                break;
            case KeyEvent.VK_ENTER:
                performCurrentItem();
                break;
        }
    }

    public void keyReleased(KeyEvent e) {

    }
}
