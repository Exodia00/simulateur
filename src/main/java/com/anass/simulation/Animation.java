package com.anass.simulation;

import com.anass.barrage.BarrageOperations;
import com.anass.models.SimulationModel;
import com.anass.models.EtatSimulation.Etat;

import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * Cette classe gère l'animation graphique de la simulation.
 * 
 * Elle dessine les éléments du barrage, les générateurs, la conduite, le réservoir, et la turbine.
 * Elle utilise JavaFX pour effectuer le rendu graphique.
 * 
 * Les animations des générateurs et de la turbine sont gérées par des objets AnimationTimer.
 * 
 * @author Anass MEHDAOUI
 */
public class Animation {
    
    /** Le canvas où l'animation se déroule */
    private Canvas canvas;

    /** Proprieté de l'état du turbo */
    private ObjectProperty<Boolean> etatTurbo = new SimpleObjectProperty<Boolean>(false);
    /** Image du turbo-altérnateur */
    private Image turbo = new Image(getClass().getResourceAsStream("/com/anass/images/turboalternateur.png"));
    /** Angle de l'image du turbo-altérnateur */
    private int angle = 0;

    /** Longueur de la base du réservoir */
    private final int LONGUEUR_RESERVOIR = 13*20; 
    /** Coordonées du réservoir */
    private final int X_RESERVOIR = 4*20;
    private final int Y_RESERVOIR_MAX = 14*20;
    private final int Y_CONDUITE_MIN = 11*20;

    /**
     * Crée une nouvelle instance de l'animation graphique.
     * 
     * @param canvas Le canvas JavaFX sur lequel l'animation sera affichée.
     * @param model Le modèle de simulation à utiliser pour l'animation.
     */
    public Animation(Canvas canvas, SimulationModel model){
        this.canvas = canvas;
        initialiser(canvas.getGraphicsContext2D(), model);
    }

    /**
     * Récupère le canvas associé à cette animation.
     * 
     * @return Le canvas JavaFX utilisé pour l'animation.
     */
    public Canvas getCanvas(){{
        return this.canvas;
    }}

    /**
     * Met à jour l'interface utilisateur de l'animation avec les données du modèle.
     * 
     * @param model Le modèle de simulation à utiliser pour la mise à jour de l'interface.
     * @param h L'heure de la simulation.
     */
    public void updateUI(SimulationModel model, int h){
        dessinerReservoir(canvas.getGraphicsContext2D(), model);
        this.etatTurbo.set(model.getEtatTurbo(h) == Etat.ACTIVE); ;
    }

    /**
     * Dessine la grille sur le canvas avec la taille de grille spécifiée.
     * 
     * @param gridSize La taille de la grille en pixels.
     */
    public void drawGrid(int gridSize) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(5);

        for (double x = 0; x < width; x += gridSize) {
            gc.strokeLine(x, 0, x, height);
        }

        for (double y = 0; y < height; y += gridSize) {
            gc.strokeLine(0, y, width, y);
        }
    }
    
    /**
     * Initialise l'animation en dessinant les composants statiques, et l'état initiale des composants dynamiques
     * 
     * @param gc le contexte graphic du canvas
     * @param model le modèle associé à la simulation
     */
    private void initialiser(GraphicsContext gc, SimulationModel model){

        dessinerApBarrage(gc);
        dessinerPlanEau(gc);
        dessinerGenerateur(gc);
        dessinerConduite(gc);
        dessinerReservoir(gc, model);
        initAnimationGenerateur(gc);
        initAnimationConduite(gc);
                
    }

    /**
     * Initialise un AnimationTimer pour dessiner le génerateur du barrage
     * 
     * @param gc le contexte graphic du canvas
     */
    private void initAnimationGenerateur(GraphicsContext gc){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now){

                dessinerCables(gc);
                dessinerEtatGenerateur(gc);

            }
        };

        timer.start();
    }

    /**
     * Initialisation d'un AnimationTimer pour dessiner la conduite du turbo-altérnateur, et le turbo altérnateur
     * 
     * @param gc le contexte graphic du canvas
     */
    private void initAnimationConduite(GraphicsContext gc){
        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                if (etatTurbo.get()) angle=(angle+2)%360; ;
                dessinerConduite(gc);
                dessinerTurbo(gc, angle);
            }
        };

        timer.start();
    }

    /**
     * Dessine l'état actuel du générateur sur le canvas.
     * 
     * @param gc Le contexte graphique JavaFX.
     */
    private void dessinerEtatGenerateur(GraphicsContext gc){
        Color clr = (this.etatTurbo.get() ? Color.GREEN : Color.RED);
        gc.setFill(clr);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        double cercleX = 19.5*20, cercleY = 9.5*20;
        int d = 20;

        gc.fillOval(cercleX, cercleY, d, d);
        gc.strokeOval(cercleX, cercleY, d, d);
    }

    /**
     * dessiner les cables de transmissions depuis le génerateur
     * 
     * @param gc
     */
    private void dessinerCables(GraphicsContext gc){
        int x1 = 20*20, y1 = 10*20;
        int x2 = 35*20, y2 = 5*20;
        int xc1 = x1 + 2*20, yc1 = y1;
        int xc2 = x2-10, yc2 = y2+20;

        gc.setStroke(this.etatTurbo.get() ? Color.YELLOW : Color.BLACK);
        gc.setLineWidth(4);

        gc.beginPath();
        gc.moveTo(x1, y1);
        gc.bezierCurveTo(xc1, yc1, xc2, yc2, x2, y2);
        gc.stroke();
    }

    /**
     * Dessiner l'image du turbo-altérnateur à un angle spécifique.
     * 
     * @param gc : Contexte graphique du canvas
     * @param angle : Angle de l'image
     */
    private void dessinerTurbo(GraphicsContext gc, int angle){
        gc.save();
        gc.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        gc.translate(0, 1.7*20);
        gc.rotate(angle);
        gc.drawImage(
            turbo, 
            -turbo.getWidth()/2, 
            (-turbo.getHeight()/2));
        gc.restore();
    }

    /**
     * Dessiner le réservoir, selon son volume, son volume maximale, et l'hauteur maximale du réservoir.
     * 
     * @param gc
     * @param model : Modèle de la simulation
     */
    private void dessinerReservoir(GraphicsContext gc, SimulationModel model){
        gc.clearRect(X_RESERVOIR, Y_RESERVOIR_MAX-LONGUEUR_RESERVOIR, LONGUEUR_RESERVOIR, LONGUEUR_RESERVOIR);

        // Dessiner le volume d'eau
        double hauteurReservoir = BarrageOperations.calculerHauteurReservoir(model, this.LONGUEUR_RESERVOIR);


        gc.setFill(Color.BLUE);

        gc.fillRect(
            X_RESERVOIR, 
            Y_RESERVOIR_MAX-hauteurReservoir, 
            LONGUEUR_RESERVOIR, 
            hauteurReservoir);

        // Contour
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(7);
        gc.strokeLine(
            X_RESERVOIR, 
            Y_RESERVOIR_MAX-LONGUEUR_RESERVOIR, 
            X_RESERVOIR, 
            Y_RESERVOIR_MAX);
        gc.strokeLine(
            X_RESERVOIR, 
            Y_RESERVOIR_MAX, 
            X_RESERVOIR+LONGUEUR_RESERVOIR, 
            Y_RESERVOIR_MAX);
        gc.strokeLine(
            X_RESERVOIR+LONGUEUR_RESERVOIR, 
            Y_RESERVOIR_MAX-LONGUEUR_RESERVOIR, 
            X_RESERVOIR+LONGUEUR_RESERVOIR, 
            Y_CONDUITE_MIN);

        // Dessiner Porte Conduite
        double hauteurPorteMax = Y_RESERVOIR_MAX - Y_CONDUITE_MIN;
        double hauteurPorte = hauteurReservoir>hauteurPorteMax ? 0 : hauteurPorteMax-hauteurReservoir;
        if (hauteurPorte > 0.0){
            gc.strokeLine(
                X_RESERVOIR+LONGUEUR_RESERVOIR, 
                Y_CONDUITE_MIN,
                X_RESERVOIR+LONGUEUR_RESERVOIR,
                Y_CONDUITE_MIN+hauteurPorte);
        }

    }
    
    /**
     * Dessiner l'arrière plan du barrage
     * 
     * @param gc
     */
    private void dessinerApBarrage(GraphicsContext gc){
        gc.setFill(Color.GRAY);

        double[] pointsX = new double[] {4*20, 17*20, 17.5*20, 22.5*20, 23*20, 29*20, 37*20, 37*20, 4*20};
        double[] pointsY = new double[] {14*20, 20, Y_CONDUITE_MIN, 13.5*20, 17.5*20, 18.5*20, 17*20, 21*20, 21*20};

        gc.fillPolygon(pointsX, pointsY, 9);

    }

    /**
     * Dessiner le plan d'eau
     * 
     * @param gc
     */
    private void dessinerPlanEau(GraphicsContext gc){

        gc.setFill(Color.BLUE);

        gc.fillRect(21*20, 
        18*20, 
        16*20, 
        2*20);


    }

    /**
     * dessiner le génerateur
     * 
     * @param gc
     */
    private void dessinerGenerateur(GraphicsContext gc){
        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        
        int rectX = 17*20, rectY = 10*20;
        int rectW = 6*20, rectH = 7*20;

        gc.fillRect(rectX, rectY, rectW, rectH);
        gc.strokeRect(rectX, rectY, rectW, rectH);

        int arcX = 17*20, arcY = 8*20;
        int arcW = 6*20, arcH = 4*20;

        gc.fillArc(arcX, arcY, arcW, arcH, 0, 180, ArcType.ROUND);
        gc.strokeArc(arcX, arcY, arcW, arcH, 0, 180, ArcType.ROUND);


    }
    
    
    private void dessinerConduite(GraphicsContext gc){

        gc.setFill(Color.BLUE);

        gc.fillRect(
            21*20,
            14*20,
            20,
            4*20);

        double[] pointsX = new double[4];
        double[] pointsY = new double[4];

        pointsX[0] = 17*20;
        pointsX[1] = 22*20;
        pointsX[2] = 21*20;
        pointsX[3] = 17*20;
        
        pointsY[0] = Y_CONDUITE_MIN;
        pointsY[1] = 14*20;
        pointsY[2] = 16*20;
        pointsY[3] = 14*20;
    
    
        gc.fillPolygon(pointsX, pointsY, 4);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(7);

        gc.strokeLine(
            X_RESERVOIR+LONGUEUR_RESERVOIR, 
            Y_CONDUITE_MIN, 
            22*20, 
            14*20);
        gc.strokeLine(
            22*20, 
            14*20, 
            22*20, 
            18*20);
        gc.strokeLine(
            X_RESERVOIR+LONGUEUR_RESERVOIR, 
            Y_RESERVOIR_MAX, 
            21*20, 
            16*20);
        gc.strokeLine(
            21*20, 
            16*20, 
            21*20, 
            20*20);
        gc.strokeLine(
            21*20, 
            20*20, 
            22*22, 
            20*20);

    }

    

}
