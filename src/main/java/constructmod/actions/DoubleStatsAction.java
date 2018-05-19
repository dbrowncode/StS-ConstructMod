   package constructmod.actions;
   
   import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
   import com.megacrit.cardcrawl.core.AbstractCreature;
   import com.megacrit.cardcrawl.core.Settings;
   import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
   import com.megacrit.cardcrawl.powers.DexterityPower;
   import com.megacrit.cardcrawl.powers.StrengthPower;
   
   public class DoubleStatsAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
   {	
		private AbstractCreature target;
		private int timesToApply = 1;
   
		public DoubleStatsAction(AbstractCreature target, int times) {
			this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
			this.duration = Settings.ACTION_DUR_XFAST;
			this.target = target;
			this.timesToApply = times;
		}
     
		public void update()
		{
			if (target.hasPower("Strength")) {
				final int str = target.getPower("Strength").amount;
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
						target,target,new StrengthPower(target,str*timesToApply),str*timesToApply));
			}
			if (target.hasPower("Dexterity")) {
				final int dex = target.getPower("Dexterity").amount;
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
						target,target,new DexterityPower(target, dex*timesToApply),dex*timesToApply));
			}
			this.isDone = true;
		}
   }