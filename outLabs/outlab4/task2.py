import numpy as np
import warnings
warnings.simplefilter("ignore")

def reconstruct_from_noisy_patches(input_dict, shape):
	i,j=shape
	values=np.zeros([i,j,6],dtype='int64')
	output=np.zeros([i,j],dtype='int64')
	#return values
	for patch_key in input_dict.keys():
		tlr,tlc,brr,brc=patch_key
		update_val=input_dict[patch_key]
		values[tlr:brr,tlc:brc,4]=update_val[:,:]
		upd_new=values[tlr:brr,tlc:brc]
		bool_black=upd_new[:,:,4]==0
		bool_white=upd_new[:,:,4]==255
		upd_new[bool_black,0]=upd_new[bool_black,0]+1
		upd_new[bool_white,1]=upd_new[bool_white,1]+1
		bool_mid=np.logical_and(upd_new[:,:,4]<255 ,upd_new[:,:,4]>0)
		upd_new[bool_mid,2]=upd_new[bool_mid,2]+1
		upd_new[bool_mid,3]=upd_new[bool_mid,3]+upd_new[bool_mid,4]
		upd_new[:,:,5]=upd_new[:,:,5]+1
		values[tlr:brr,tlc:brc]=upd_new[:,:]
		
	bool_not_b_more_w=np.logical_and(values[:,:,5]!=0 , np.logical_and(values[:,:,2]==0 ,values[:,:,0]<=values[:,:,1]))
	output[bool_not_b_more_w]=255
	bool_mid_exists=values[:,:,2]!=0
	bool_b_more_w=np.logical_or(values[:,:,5]==0,np.logical_and(values[:,:,2]==0 , values[:,:,0]>values[:,:,1]))
	output[bool_mid_exists]=values[bool_mid_exists,3]//values[bool_mid_exists,2]
	# print(output[bool_mid_exists])
	# print(output[bool_not_b_more_w])
	# print(output[bool_b_more_w])
	# print(output.shape)
	# print(output.ndim)
	# print(output.dtype)
	output=output.astype('int64')
	return output
# return the reconstructed image
# import numpy as np
# def reconstruct_from_noisy_patches(input_dict, shape):
#     # Initialization: Initialise M, black_count, mid_count, white_count, mid_total
#     i,j=shape
# 	values=np.zeros([i,j,6],dtype='int64')
# 	output=np.zeros([i,j],dtype='int64')
#     for topleft_row, topleft_col, bottomright_row, bottomright_col in input_dict: # no loop except this!
#         tlr, tlc, brr, brc = topleft_row, topleft_col, bottomright_row, bottomright_col
#         patch = input_dict[(tlr, tlc, brr, brc)]
#         values[tlr:brr,tlc:brc,4]=patch[:,:]
# 		bool_black=values[tlr:brr,tlc:brc,4]==0
# 		bool_white=values[tlr:brr,tlc:brc,4]==255
# 		bool_mid=values[tlr:brr,tlc:brc,4]<255 and values[tlr:brr,tlc:brc,4]>0
# 		values[bool_black][0]=values[bool_black][0]+1
# 		values[bool_white][1]=values[bool_white][1]+1
# 		values[bool_mid][2]=values[bool_mid][2]+1
# 		values[tlr:brr,tlc:brc,3]=values[tlr:brr,tlc:brc,3]+values[tlr:brr,tlc:brc,4]
# 		values[tlr:brr,tlc:brc,5]=values[tlr:brr,tlc:brc,5]+1
#     bool_b_more_w=values[:,:,5]==0 or (values[:,:,2]==0 and values[:,:,0]>values[:,:,1])
# 	output[bool_b_more_w]=0
# 	bool_not_b_more_w=values[:,:,5]!=0 and values[:,:,2]==0 and values[:,:,0]<=values[:,:,1]
# 	output[bool_not_b_more_w]=255
# 	bool_mid_exists=[:,:,2]!=0
# 	output[bool_mid_exists]=values[bool_mid_exists][3]/values[bool_mid_exists][2]
# 	return output    
#         # change black_count, mid_count, white_count, mid_total here
#     # Finally change M here
#     #return M # You have to return the reconstructed matrix (M).
