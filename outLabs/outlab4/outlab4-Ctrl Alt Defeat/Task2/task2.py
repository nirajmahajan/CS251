import numpy as np
import warnings

def reconstruct_from_noisy_patches(input_dict, shape):
    i,j=shape
    values=np.zeros([i,j,6],dtype='int64')
    output=np.zeros([i,j],dtype='int64')
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
    output[bool_mid_exists]=np.around(values[bool_mid_exists,3]/values[bool_mid_exists,2])
    output=output.astype('int64')
    return output
