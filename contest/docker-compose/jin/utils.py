from typing import Any
from uuid import UUID
from langchain_core.outputs import ChatGenerationChunk, GenerationChunk
import streamlit as st
from langchain_core.callbacks.base import BaseCallbackHandler


class StreamHandler(BaseCallbackHandler):
    def __init__(self, container, initial_text=""):
        self.container = container
        self.text = initial_text
        
    def on_llm_new_token(self, token: str, **kwargs) -> None:
        self.text += token
        self.container.markdown(self.text)
        
        
def print_messages():
    # 이전 대화기록 출력
    if "messages" in st.session_state and len(st.session_state["messages"]) > 0:
        for chat_message in st.session_state["message"]:
            st.chat_message(chat_message.role).write(chat_message.content)