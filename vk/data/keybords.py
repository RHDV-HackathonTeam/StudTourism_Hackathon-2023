from vkbottle import Keyboard, KeyboardButtonColor, Text, OpenLink, Callback
from data import strings as mp

keyboard_hello = (
    Keyboard(inline=True)
        .add(Text('Меню'), color=KeyboardButtonColor.PRIMARY)
).get_json()

keyboard_menu = (
    Keyboard(one_time=False, inline=False)
        .add(Text('Мероприятия'), color=KeyboardButtonColor.POSITIVE)
        .add(Text('Настройки'), color=KeyboardButtonColor.POSITIVE)
        .row()
        .add(Text('Пригласить'), color=KeyboardButtonColor.POSITIVE)
        .add(Text('Статус'), color=KeyboardButtonColor.POSITIVE)
).get_json()

keyboard_notice_on = (
    Keyboard(inline=True)
        .add(Callback('Включить', payload={"cmd": "on"}))
        .row()
        .add(Callback('Назад', payload={"cmd": "back"}))
).get_json()

keyboard_notice_off = (
    Keyboard(inline=True)
        .add(Callback('Выключить', payload={"cmd": "off"}))
        .row()
        .add(Callback('Назад', payload={"cmd": "back"}))
).get_json()

keyboard_subscribe = (
    Keyboard(inline=True)
    .add(color=KeyboardButtonColor.POSITIVE, action=OpenLink(link=mp.url_sub, label="Подпишись"))
    .row()
    .add(Text('Подтвердить'), color=KeyboardButtonColor.POSITIVE)
)
