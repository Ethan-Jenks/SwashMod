package theSwashbuckler.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theSwashbuckler.SwashMod;
import theSwashbuckler.characters.TheSwashbuckler;
import theSwashbuckler.powers.AmmoPower;

import static theSwashbuckler.SwashMod.makeCardPath;

public class CripplingShot extends AbstractDynamicCard {

    // TEXT DECLARATION
    public static final String ID = SwashMod.makeID(CripplingShot.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("CripplingShot.png");// "public static final String IMG = makeCardPath("Crippling_Shot.png");


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheSwashbuckler.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final int VULNERABLE = 1;
    private static final int UPGRADE_PLUS_VULNERABLE = 1;

    // /STAT DECLARATION/


    public CripplingShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = VULNERABLE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(AmmoPower.POWER_ID)) {
            if (p.getPower(AmmoPower.POWER_ID).amount > 0) {
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(m, p,
                                new VulnerablePower(m, magicNumber, false), magicNumber));
                p.getPower(AmmoPower.POWER_ID).amount --;
            } else {
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                                new AmmoPower(AbstractDungeon.player, AbstractDungeon.player,1), 1));
            }
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if(AbstractDungeon.player.hasPower(AmmoPower.POWER_ID) && AbstractDungeon.player.getPower(AmmoPower.POWER_ID).amount > 0) {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_VULNERABLE);
            initializeDescription();
        }
    }
}
