#include "com_jack_zhou_bili_util_JNIClass.h"




//============================================aes encoding======================================================
/**
* S box
* replace the promary data
**/
void replace(unsigned char data[][4]){
	int i = 0;
	int j = 0;
	for(i = 0; i < 4 ; i++){
		for(j = 0; j < 4; j++){
			data[i][j] = sBox[data[i][j]];
		}
	}
}

/**
* move row
* 0 row -- left move 0  
* 1 row -- left move 1
* 2 row -- left move 2
* 3 row -- left move 3
**/
void shiftRow(unsigned char data[][4]){
	int i = 0;
	int j = 0;
	unsigned char temp[4];

	for(i = 1 ; i < 4; i++){
		for(j = 0; j < 4; j++){
			temp[j] = data[i][(i+j) % 4];
		}

		for(j = 0; j < 4 ; j++){
			data[i][j] = temp[j];
		}
	}
}


/**
* cow mix  -- lie hun xiao
**/
//=========================================================================================
void mixColum(unsigned char data[][4]){
	unsigned char temp[4];
	int i = 0;
	int j = 0;
	
	for(i = 0 ; i < 4; i++){
		for(j = 0; j < 4; j++){
			temp[j] = data[j][i];		//select every cloums
		}

		//calc every mix value
		for(j = 0; j < 4; j++){
			data[j][i] = FFmul(0x02, temp[j]) ^ FFmul(0x03, temp[(j+1)%4]) ^ FFmul(0x01, temp[(j+2)%4]) ^ FFmul(0x01, temp[(j+3)%4]);
		}
		
	}
}
/**
* GF(2^8) 
**/
unsigned char FFmul(unsigned char a, unsigned char data){
	unsigned char temp[4];
	unsigned char i = 0;
	unsigned char ret = 0;
	temp[0] = data;
	for(i = 1; i < 4; i++){
		temp[i] = temp[i-1] << 1;			//left move
		if(temp[i-1] & 0x80){				//before left move, the high bit is 1
			temp[i] = temp[i] ^ 0x1b;
		}
	}
	//this place i don't understand
	for(i = 0 ; i < 4; i++){
		if((a >> i) & 0x01){
			ret = ret ^ temp[i];
		}
	}
	return ret;
}

//=========================================================================================

/**
* bit add 
**/
void addRoundKey(unsigned char data[][4], unsigned char temp[][4]){
	int i = 0;
	int j = 0;
	
	for(i = 0 ; i < 4; i++){
		for(j = 0; j < 4; j++){
			data[j][i] = data[j][i] ^ temp[j][i];
		}
	}
}

/**
* modify key
**/
void keyExpansion(unsigned char *key, unsigned char w[][4][4]){

    int i,j,r,c;
    unsigned char rc[] = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36};
    //put the primary key into the w[0]
    for(r=0; r<4; r++)
    {
        for(c=0; c<4; c++)
        {
            w[0][r][c] = key[r+c*4];
        }
    }

    for(i=1; i<=10; i++)
    {
        for(j=0; j<4; j++)
        {
            unsigned char t[4];
            for(r=0; r<4; r++)
            {
                t[r] = j ? w[i][r][j-1] : w[i-1][r][3];
            }
            if(j == 0)
            {
                unsigned char temp = t[0];
                for(r=0; r<3; r++)
                {
                    t[r] = sBox[t[(r+1)%4]];
                }
                t[3] = sBox[temp];
                t[0] ^= rc[i-1];
            }
            for(r=0; r<4; r++)
            {
                w[i][r][j] = w[i-1][r][j] ^ t[r];
            }
        }
      }
}

/**
* encode the data
**/
unsigned char* encoding(unsigned char *input){

	unsigned char state[4][4];
	int i,r,c;
	//move the input to data[][4]	
	for(r=0; r<4; r++)
	{
		for(c=0; c<4 ;c++)
		{
			state[r][c] = input[c*4+r];
		}
	}

	addRoundKey(state,keySpan[0]);

	for(i=1; i<=10; i++)
	{
		replace(state);
		shiftRow(state);
		if(i!=10)
			mixColum(state);
		addRoundKey(state,keySpan[i]);
	}

	for(r=0; r<4; r++)
	{
		for(c=0; c<4 ;c++)
		{
			input[c*4+r] = state[r][c];
		}
	}
	return input;
 
}

//============================================aes encoding======================================================


//============================================aes decoding======================================================
/**
* replace with invsBox
**/
void rever_replace(unsigned char data[][4]){
	int i = 0;
	int j = 0;
	for(i = 0; i < 4; i++){
		for(j = 0; j < 4; j++){
			data[i][j] = invsBox[data[i][j]];
		}
	}
}

/**
* decoding -----  shift rows
**/
void rever_shiftRow(unsigned char data[][4]){
	int i,j;
	unsigned char temp[4];

	for(i = 1; i < 4; i++){
		for(j = 0; j < 4; j++){
			temp[j] = data[i][(j - i + 4)%4];
		}

		for(j = 0; j < 4; j++){
			data[i][j] = temp[j];
		}
	}
}

/**
* decoding -----  shift rows
**/
void rever_mixColum(unsigned char data[][4]){
	int i,j;
	int temp[4];
	
	for(i = 0 ; i < 4; i++){
		for(j = 0; j < 4; j++){
			temp[j] = data[j][i];
		}

		for(j = 0; j < 4; j++){
			data[j][i] = FFmul(0x0e, temp[j]) ^ FFmul(0x0b, temp[(j+1)%4]) ^ FFmul(0x0d, temp[(j+2)%4]) ^ FFmul(0x09, temp[(j+3) %4]);
		}
	}
}


/**
* decoding -----  shift rows
**/
unsigned char* decoding(unsigned char *input){
	unsigned char data[4][4];
	int i,j;
	
	for(i = 0 ; i < 4; i++){
		for(j = 0; j < 4; j++){
			data[i][j] = input[i + 4 * j];
		}
	}

	addRoundKey(data, keySpan[10]);

	for(i = 9; i >= 0; i--){
		rever_shiftRow(data);
		rever_replace(data);
		addRoundKey(data,keySpan[i]);
		if(i){
			rever_mixColum(data);
		}
	}

	for(i = 0; i < 4; i++){
		for(j = 0; j < 4; j++){
			input[j*4 + i] = data[i][j];
		}
	}

	return input;	
}

//============================================aes decoding======================================================

void print_key(unsigned char w[][4][4]){
	int i,j,k;
	for(i = 0; i < 11; i++){
		for(j = 0; j < 4; j++){
			printf("keySpan[%d][%d] ---------------------------------------- \n", i, j);
			for(k = 0; k < 4; k++){
				printf(" 0x%2x ", w[i][j][k]);
			}
			printf("\n ----------------------------------------------------- \n");
		}
	}

}


//扩展输入的数据为16的倍数
unsigned char * encodeData(unsigned char *input){
	if(NULL == input){
		printf("the input data is NULL\n");
		return NULL;
	}

	int len = strlen(input) / 16;
	if((strlen(input)) % 16 != 0 ){         //如果不是16的倍数，说明输入数据后面还有数据
		len++;
	}
	printf("we need len is %d\n", len);
	unsigned char *data_addr = (unsigned char*)malloc((16 * len * sizeof(unsigned char)) + 1);  //添加一个字符串结束符的空间
	if(data_addr != NULL){
		strcpy(data_addr, input);
	}
	int i = 0;
	len = strlen(data_addr);
	LOGI("encode length --- %d", len);
	for(i = 0; i < len; i+=16){

		encoding((data_addr + i));
		LOGI(" %d ", i);
	}


	return data_addr;
}

unsigned char * decodeData(unsigned char* input){
	unsigned char* data = input;
	int length = strlen(input);
	int i = 0;
	LOGI("decode length -- %d", length);
	for(i = 0; i < length; i+=16){
		decoding(data+i);
		LOGI(" %d ", i);
	}

	return input;
}

JNIEXPORT jbyteArray JNICALL Java_com_jack_zhou_bili_util_JNIClass_encoding(JNIEnv *env, jclass jobj, jstring jstr){
	const unsigned char *data = (*env)->GetStringUTFChars(env, jstr, NULL);
	if(NULL == data){
		LOGI("user info----name:%s, age:%d, sex:%s.", "null", 18, "男");
	}

	keyExpansion(key, keySpan);
	LOGI("primary data --- : %s", data);
	unsigned char *input = encodeData(data);

	LOGI("enoding data ---- : %s", input);

	jbyteArray array = (*env)->NewByteArray(env, strlen(input));
	(*env)->SetByteArrayRegion(env, array, 0, strlen(input), input);
	free(input);
	return array;
}



JNIEXPORT jstring JNICALL Java_com_jack_zhou_bili_util_JNIClass_decoding(JNIEnv *env, jclass jobj, jbyteArray jbyte){
	const char *data = (*env)->GetByteArrayElements(env, jbyte, NULL);
	if(data == NULL){
		LOGI("decoding error %s", "null");
		return (*env)->NewStringUTF(env,"null");
	}

	LOGI("decoding data ---- : %s", decodeData(data));

	return (*env)->NewStringUTF(env, "asda");
}

