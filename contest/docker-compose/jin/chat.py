import streamlit as st
from langchain.callbacks.streaming_stdout import StreamingStdOutCallbackHandler
from langchain.callbacks.manager import CallbackManager
from langchain_community.llms import ollama
from langchain_community.chat_models import ollama
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate
from utils import print_messages, StreamHandler


# llama3-8B 모델 생성
llm =ollama.ChatOllama(
        model="llama3", 
        base_url="http://ollama:11434",
        verbose=True,
        callback_manager=CallbackManager([StreamingStdOutCallbackHandler()])
)

def get_response(user_input):
    global llm
    # Prompt 생성
    prompt = ChatPromptTemplate.from_messages(
        [
            ("system", "You are helpful ai assistant. If possible, please answer in Korean "), 
            ("user", "{question}")
        ]
    )
    chain = prompt | llm | StrOutputParser()
    # LLM 답변 생성
    response = chain.invoke({"question": user_input})
    return response


st.title("Chat with Llama3")
if "messages" not in st.session_state.keys():
    st.session_state["messages"] = [
        {"role": "assistant", "content": "저에게 질문을 해주세요."}
    ]

if user_input := st.chat_input("메세지를 입력해 주세요."):
    # 사용자 입력
    st.session_state.messages.append({"role": "user", "content": user_input})
    # st.chat_message("user").write(f"{user_input}")
    # st.session_state["messages"].append(ChatMessage(role="user", content=user_input))

for message in st.session_state.messages:
    with st.chat_message(message["role"]):
        st.write(message["content"])
        
if st.session_state.messages[-1]["role"] != "assistant":
    with st.chat_message("assistant"):
        with st.spinner("답변 생성중..."):
            stream_handler = StreamHandler(st.empty())
            response = get_response(user_input)
            st.write(response)
            message = {"role": "assistant", "content": response}
            st.session_state.messages.append(message)