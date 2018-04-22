package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import constructmod.patches.AbstractCardEnum;

public class GuardOrb extends AbstractCycleCard {
	public static final String ID = "GuardOrb";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 0;
	private static final int GAIN_BLOCK = 2;
	//private static final int UPGRADE_PLUS_GAIN_BLOCK = 2;
	private static final int POOL = 1;

	public GuardOrb() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.baseBlock = this.block = GAIN_BLOCK;
	}
	
	@Override
	public void atTurnStart(){
		hasCycled = false;
	}
	
	@Override
	public void triggerWhenDrawn(){
		AbstractPlayer p = AbstractDungeon.player;
		
		if (hasCycled) return;
		
		flash();
		
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
		if (upgraded) AbstractDungeon.player.discardPile.addToTop(this.makeCopy());
		if (megaUpgraded) AbstractDungeon.player.discardPile.addToTop(this.makeCopy());
		
		cycle();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,this.block));
		if (upgraded) AbstractDungeon.player.discardPile.addToTop(this.makeCopy());
		if (megaUpgraded) AbstractDungeon.player.discardPile.addToTop(this.makeCopy());
	}

	@Override
	public AbstractCard makeCopy() {
		return new GuardOrb();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = DESCRIPTION + M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
