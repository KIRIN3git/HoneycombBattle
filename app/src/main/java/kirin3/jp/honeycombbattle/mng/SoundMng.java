package kirin3.jp.honeycombbattle.mng;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import kirin3.jp.honeycombbattle.R;
import kirin3.jp.honeycombbattle.util.LogUtils;

public class SoundMng {

    private static final String TAG = LogUtils.makeLogTag(SoundMng.class);

    private static SoundPool sSoundPool2, sSoundPool1;
    private static int sSoundStart1, sSoundStart2, sSoundGameOver;
    private static int sSoundBomb1, sSoundBomb2, sSoundLaser1, sSoundLaser2, sSoundWave1, sSoundWave2;
    private static int sSoundSpeed1, sSoundSpeed2, sSoundUnrivared1, sSoundUnrivared2;
    private static int sSoundDead;

    public static void soundInit(Context context) {
        sSoundPool1 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sSoundPool2 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sSoundStart1 = sSoundPool1.load(context, R.raw.se_maoudamashii_retro02, 0);
        sSoundStart2 = sSoundPool1.load(context, R.raw.se_maoudamashii_system24, 0);
        sSoundGameOver = sSoundPool1.load(context, R.raw.se_maoudamashii_chime05, 0);
        sSoundDead = sSoundPool1.load(context, R.raw.powerdown07, 0);
        sSoundBomb1 = sSoundPool2.load(context, R.raw.bomb, 0);
        sSoundBomb2 = sSoundPool2.load(context, R.raw.explosion3, 0);
        sSoundLaser1 = sSoundPool2.load(context, R.raw.launcher1, 0);
        sSoundLaser2 = sSoundPool2.load(context, R.raw.launcher3, 0);
        sSoundWave1 = sSoundPool2.load(context, R.raw.beam0, 0);
        sSoundWave2 = sSoundPool2.load(context, R.raw.beam2, 0);
        sSoundSpeed1 = sSoundPool2.load(context, R.raw.magic_wave1, 0);
        sSoundSpeed2 = sSoundPool2.load(context, R.raw.magic4, 0);
        sSoundUnrivared1 = sSoundPool2.load(context, R.raw.powerup01, 0);
        sSoundUnrivared2 = sSoundPool2.load(context, R.raw.powerup08, 0);
    }

    public static void soundEnd() {
        sSoundPool1.release();
        sSoundPool2.release();
    }

    public static void playSoundStart1() {
        sSoundPool1.play(sSoundStart1, 1.0F, 1.0F, 0, 0, 1.0F);
    }

    public static void playSoundStart2() {
        sSoundPool1.play(sSoundStart2, 1.0F, 1.0F, 0, 0, 1.0F);
    }

    public static void playSoundGameOver() {
        sSoundPool1.play(sSoundGameOver, 1.0F, 1.0F, 0, 0, 1.0F);
    }

    public static void playSoundDead() {
        sSoundPool1.play(sSoundDead, 1.0F, 1.0F, 0, 0, 1.0F);
    }

    public static void playSoundBomb(int level) {
        switch (level) {
            case 1:
                sSoundPool2.play(sSoundBomb1, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                sSoundPool2.play(sSoundBomb2, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            default:
                break;
        }
    }

    public static void playSoundLeaser(int level) {
        switch (level) {
            case 1:
                sSoundPool2.play(sSoundLaser1, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                sSoundPool2.play(sSoundLaser2, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            default:
                break;
        }
    }

    public static void playSoundWave(int level) {
        switch (level) {
            case 1:
                sSoundPool2.play(sSoundWave1, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                sSoundPool2.play(sSoundWave2, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            default:
                break;
        }
    }

    public static void playSoundSpeedUp(int level) {
        switch (level) {
            case 1:
                sSoundPool2.play(sSoundSpeed1, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                sSoundPool2.play(sSoundSpeed2, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            default:
                break;
        }
    }


    public static void playSoundUnrivaled(int level) {
        switch (level) {
            case 1:
                sSoundPool2.play(sSoundUnrivared1, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                sSoundPool2.play(sSoundUnrivared2, 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            default:
                break;
        }
    }

}
