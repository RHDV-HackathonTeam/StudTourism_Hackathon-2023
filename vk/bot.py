from vkbottle import Bot
from handlers import start_labeler, base_labeler
from config import api, state_dispenser, labeler


labeler.load(start_labeler)
labeler.load(base_labeler)

bot = Bot(
    api=api,
    labeler=labeler,
    state_dispenser=state_dispenser,
)
bot.run_forever()