package constructmod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.Random;

public class InstantDamageRandomEnemyAction extends AbstractGameAction {
    private DamageInfo info;
    public boolean offsetVisualsRandomly = false;
    public static final Random random = new Random();

    public InstantDamageRandomEnemyAction(DamageInfo info, AttackEffect effect) {
        this(info,effect,false);
    }

    public InstantDamageRandomEnemyAction(DamageInfo info, AttackEffect effect, boolean offsetVisualsRandomly) {
        this.info = info;
        this.setValues(AbstractDungeon.getMonsters().getRandomMonster(true), info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.offsetVisualsRandomly = offsetVisualsRandomly;
    }

    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
        } else {
            this.target.damageFlash = true;
            this.target.damageFlashFrames = 4;
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(
                    this.target.hb.cX + (offsetVisualsRandomly?random.nextFloat()*this.target.hb_w - this.target.hb_w*0.5f:0f),
                    this.target.hb.cY + (offsetVisualsRandomly?random.nextFloat()*this.target.hb_h - this.target.hb_h*0.5f:0f),
                    this.attackEffect));

            if (this.attackEffect == AttackEffect.POISON) {
                this.target.tint.color = Color.CHARTREUSE.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
            } else if (this.attackEffect == AttackEffect.FIRE) {
                this.target.tint.color = Color.RED.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
            }

            this.target.damage(this.info);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            this.isDone = true;
        }
    }
}
