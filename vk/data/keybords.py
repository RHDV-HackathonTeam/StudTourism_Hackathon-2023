from vkbottle import Keyboard, KeyboardButtonColor, Text, OpenLink

keyboard_hello = (
    Keyboard(inline=True)
        .add(Text('Меню'), color=KeyboardButtonColor.PRIMARY)
).get_json()

keyboard_menu = (
    Keyboard(one_time=False, inline=False)
        .add(Text('Мероприятия'), color=KeyboardButtonColor.POSITIVE)
        .add(Text('Настройки'), color=KeyboardButtonColor.PRIMARY)
        .row()
        .add(Text('Пригласить'), color=KeyboardButtonColor.PRIMARY)
        .add(Text('Статус'), color=KeyboardButtonColor.PRIMARY)
).get_json()

keyboard_notice_on = (
    Keyboard(inline=True)
        .add(Text('Включить'), color=KeyboardButtonColor.POSITIVE)
        .row()
        .add(Text('Назад'), color=KeyboardButtonColor.PRIMARY)
).get_json()

keyboard_notice_off = (
    Keyboard(inline=True)
        .add(Text('Выключить'), color=KeyboardButtonColor.POSITIVE)
        .row()
        .add(Text('Назад'), color=KeyboardButtonColor.PRIMARY)
).get_json()

keyboard_subscribe = (
    Keyboard(inline=True)
    .add(Text('Подпишись'), color=KeyboardButtonColor.POSITIVE)
    .row()
    .add(Text('Подтвердить'), color=KeyboardButtonColor.PRIMARY, action=OpenLink(link=""))
)
