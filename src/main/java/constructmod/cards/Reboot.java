package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import basemod.abstracts.CustomCard;
import constructmod.patches.AbstractCardEnum;

public class Reboot extends AbstractConstructCard {
	public static final String ID = "Reboot";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int DRAW_CARDS = 3;
	private static final int UPGRADE_PLUS_DRAW_CARDS = 2;
	private static final int M_UPGRADE_PLUS_DRAW_CARDS = 2;
	private static final int POOL = 1;

	public Reboot() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.NONE, POOL);
		this.magicNumber = this.baseMagicNumber = DRAW_CARDS;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, 99, false));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Reboot();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_DRAW_CARDS);
		} else if (this.canUpgrade()) {
			this.megaUpgradeName();
			this.upgradeMagicNumber(M_UPGRADE_PLUS_DRAW_CARDS);
		}
	}
}
