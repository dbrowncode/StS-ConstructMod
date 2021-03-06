package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.MeltdownPower;

public class Meltdown extends AbstractConstructCard {
	public static final String ID = ConstructMod.makeID("Meltdown");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int DMG_PER_TURN = 12;
	private static final int UPGRADE_DMG_PER_TURN = 2;
	private static final int M_UPGRADE_DMG_PER_TURN = 4;
	private static final int POOL = 1;

	public Meltdown() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE, POOL);
		this.magicNumber = this.baseMagicNumber = DMG_PER_TURN;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MeltdownPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Meltdown();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_DMG_PER_TURN);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_DMG_PER_TURN);
		}
	}
}
