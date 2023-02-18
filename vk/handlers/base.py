from vkbottle.bot import BotLabeler, Message, rules
from database import dbworker as db
from data import strings as mp
from data import keybords as kb
from config import api
from utils import utils

base_labeler = BotLabeler()
base_labeler.vbml_ignore_case = True
base_labeler.auto_rules = [rules.PeerRule(from_chat=False)]

@base_labeler.message(text="Мероприятия")
async def check_events(message: Message):
    # title = data["title"]
    # pic_url = data["picture_url"]
    # text = data["text"]
    # tags = data["tags"]
    # await bot.send_photo(chat_id=message.chat.id, photo=pic_url, caption=text)
    pass
    

@base_labeler.message(text="Настройки")
async def change_config(message: Message):
    user = db.get_by_id(message.from_id)
    if not user.notification:
        db.change_user_notice(message.from_id, True)
        await message.answer(message=mp.on_notice_string, keyboard=kb.keyboard_notice_on)
    else:
        db.change_user_notice(message.from_id, False)
        await message.answer(message=mp.off_notice_string, keyboard=kb.keyboard_notice_off)

@base_labeler.message(text="Пригласить")
async def invite_friends(message: Message):
    pass

@base_labeler.message(text="Статус")
async def check_status(message: Message):
    check_member = await api.groups.is_member(group_id=message.group_id,user_id=message.from_id)
    if check_member:
        await message.answer(message=mp.status_string, keyboard=kb.keyboard_subscribe)
    else:
       await message.answer(message=mp.subcribe_string, keyboard=kb.keyboard_menu)