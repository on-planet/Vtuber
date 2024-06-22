# -*- coding: utf-8 -*-
import asyncio
import http.cookies
import random
import socket
import os
from typing import *

import aiohttp

import blivedm
import blivedm.models.web as web_models

# 直播间ID的取值看直播间URL
TEST_ROOM_IDS = [
    1321846
]

# 这里填一个已登录账号的cookie的SESSDATA字段的值。不填也可以连接，但是收到弹幕的用户名会打码，UID会变成0
SESSDATA = ''

session: Optional[aiohttp.ClientSession] = None

async def main():
    init_session()
    try:
        await run_single_client()
        # await run_multi_clients()
    finally:
        await session.close()

def init_session():
    cookies = http.cookies.SimpleCookie()
    cookies['SESSDATA'] = SESSDATA
    cookies['SESSDATA']['domain'] = 'bilibili.com'

    global session
    session = aiohttp.ClientSession()
    session.cookie_jar.update_cookies(cookies)


async def run_single_client():
    """
    演示监听一个直播间
    """
    room_id = random.choice(TEST_ROOM_IDS)
    client = blivedm.BLiveClient(room_id, session=session)
    handler = MyHandler()
    client.set_handler(handler)

    client.start()
    try:
        # 演示5秒后停止
        await asyncio.sleep(5)
        # client.stop()

        await client.join()
    finally:
        await client.stop_and_close()

class MyHandler(blivedm.BaseHandler):

    # def _on_heartbeat(self, client: blivedm.BLiveClient, message: web_models.HeartbeatMessage):
    #     print(f'[{client.room_id}] 心跳')

    def _on_danmaku(self, client: blivedm.BLiveClient, message: web_models.DanmakuMessage):
        print(f'[{client.room_id}] {message.uname}：{message.msg}')
        self.save_message_to_file(message.msg)
    # def _on_gift(self, client: blivedm.BLiveClient, message: web_models.GiftMessage):
    #     print(f'[{client.room_id}] {message.uname} 赠送{message.gift_name}x{message.num}'
    #           f' （{message.coin_type}瓜子x{message.total_coin}）')

    # def _on_buy_guard(self, client: blivedm.BLiveClient, message: web_models.GuardBuyMessage):
    #     print(f'[{client.room_id}] {message.username} 购买{message.gift_name}')

    def _on_super_chat(self, client: blivedm.BLiveClient, message: web_models.SuperChatMessage):
        print(f'[{client.room_id}] 醒目留言 ¥{message.price} {message.uname}：{message.message}')

    def save_message_to_file(self, message: str):
        try:
            # 创建日志目录，如果不存在的话
            os.makedirs('logs', exist_ok=True)
            print("日志目录 'logs' 已创建或已存在。")  # 调试信息

            # 写入文件
            file_path = 'E:/code/Java/Java_course_project_AI-Vtuber/src/main/resources/text_data/blive.txt'
            with open(file_path, 'a', encoding='utf-8') as file:
                file.write(message + '\n')
            print(f"消息已写入文件：{9}")  # 调试信息
        except Exception as e:
            print(f"保存消息到文件时出错: {e}")  # 捕获并打印任何错误

if __name__ == '__main__':
    asyncio.run(main())
