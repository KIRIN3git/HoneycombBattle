package kirin3.jp.honeycombbattle.mng;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import kirin3.jp.honeycombbattle.R;

public class SoundMng {


    private static SoundPool sSoundPool1,sSoundPool2;
    private static int sSoundBomb1,sSoundBomb2,sSoundLaser1,sSoundLaser2,sSoundWave1,sSoundWave2;
    private static int sSoundSpeed1,sSoundSpeed2,sSoundUnrivared1,sSoundUnrivared2;
    private static int sSoundDead;

    public static void soundInit(Context context){
        sSoundPool1 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sSoundPool2 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sSoundBomb1 = sSoundPool1.load(context, R.raw.bomb, 0);
        sSoundBomb2 = sSoundPool1.load(context, R.raw.explosion3, 0);
        sSoundLaser1 = sSoundPool1.load(context, R.raw.launcher1, 0);
        sSoundLaser2 = sSoundPool1.load(context, R.raw.launcher3, 0);
        sSoundWave1 = sSoundPool1.load(context, R.raw.beam0, 0);
        sSoundWave2 = sSoundPool1.load(context, R.raw.beam2, 0);
        sSoundSpeed1 = sSoundPool1.load(context, R.raw.magic_wave1, 0);
        sSoundSpeed2 = sSoundPool1.load(context, R.raw.magic4, 0);
        sSoundUnrivared1 = sSoundPool1.load(context, R.raw.powerup01, 0);
        sSoundUnrivared2 = sSoundPool1.load(context, R.raw.powerup08, 0);
        sSoundDead = sSoundPool2.load(context, R.raw.powerdown07, 0);
    }

    public static void soundEnd(){
        sSoundPool1.release();
        sSoundPool2.release();
    }

    public static void playSoundBomb(int level) {
        switch (level) {
            case 1:
                sSoundPool1.play(sSoundBomb1, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                sSoundPool1.play(sSoundBomb2, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            default:
                break;
        }
    }

    public static void playSoundLeaser(int level) {
        switch (level) {
            case 1:
                sSoundPool1.play(sSoundLaser1, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                sSoundPool1.play(sSoundLaser2, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            default:
                break;
        }
    }

    public static void playSoundWave(int level) {
        switch (level) {
            case 1:
                sSoundPool1.play(sSoundWave1, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                sSoundPool1.play(sSoundWave2, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            default:
                break;
        }
    }

    public static void playSoundSpeedUp(int level) {
        switch (level) {
            case 1:
                sSoundPool1.play(sSoundSpeed1, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                sSoundPool1.play(sSoundSpeed2, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            default:
                break;
        }
    }


    public static void playSoundUnrivaled(int level) {
        switch (level) {
            case 1:
                sSoundPool1.play(sSoundUnrivared1, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                sSoundPool1.play(sSoundUnrivared2, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            default:
                break;
        }
    }

    public static void playSoundDead() {

        sSoundPool2.play(sSoundDead, 1.0F, 1.0F, 0, 0, 1.0F);

    }
}
