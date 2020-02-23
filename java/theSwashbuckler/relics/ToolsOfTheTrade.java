package theSwashbuckler.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theSwashbuckler.SwashMod;
import theSwashbuckler.powers.AmmoPower;
import theSwashbuckler.powers.MomentumPower;
import theSwashbuckler.powers.PreparationPower;
import theSwashbuckler.util.TextureLoader;

import static theSwashbuckler.SwashMod.makeRelicOutlinePath;
import static theSwashbuckler.SwashMod.makeRelicPath;

public class ToolsOfTheTrade extends CustomRelic {


    // ID, images, text.
    public static final String ID = SwashMod.makeID("ToolsOfTheTrade");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    private static final int MAGIC_NUMBER = 1;

    public ToolsOfTheTrade() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new AmmoPower(AbstractDungeon.player, AbstractDungeon.player,MAGIC_NUMBER), 1));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if(card.type.equals(AbstractCard.CardType.ATTACK)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new MomentumPower(AbstractDungeon.player, AbstractDungeon.player,MAGIC_NUMBER), 1));
        }
        if(card.type.equals(AbstractCard.CardType.SKILL)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new PreparationPower(AbstractDungeon.player, AbstractDungeon.player,MAGIC_NUMBER), 1));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
