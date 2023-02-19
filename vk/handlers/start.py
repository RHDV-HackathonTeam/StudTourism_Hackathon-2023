from vkbottle.bot import BotLabeler, Message, rules
from vkbottle import BaseStateGroup
from database import dbworker as db
from data import strings as mp
from data import keybords as kb
from utils import utils


class StartStates(BaseStateGroup):
    NOTICE_MENU_STATE = "notice_menu"
    

start_labeler = BotLabeler()
start_labeler.vbml_ignore_case = True
start_labeler.auto_rules = [rules.PeerRule(from_chat=False)]

@start_labeler.message(text="Начать")
async def send_hello(message: Message):
    try:
        uuid = message.ref
        date = utils.date_today()
        if uuid != None:
            user = await message.get_user()
            print(user.first_name)
            register_status = db.init_user(uuid=uuid, chat_id=message.from_id, username=user.nickname, notification=True, date_of_join=date)
            if register_status['message'] == 'User already added':
                await message.answer(message=mp.already_reg_string, keyboard=kb.keyboard_menu)
            else:
                await message.answer(message=mp.hello_string, keyboard=kb.keyboard_menu)
        else:
            if db.get_by_id(message.from_id) != None:
                await message.answer(message=mp.welcome_string, keyboard=kb.keyboard_menu)
            else:
                await message.answer(message=mp.need_reg_string, keyboard=kb.keyboard_menu)
    except Exception as e:
        await message.answer(message=f"{e}")


