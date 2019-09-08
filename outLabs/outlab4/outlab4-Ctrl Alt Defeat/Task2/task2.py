import numpy as np

# 0 corresponds to black_count
# 1 corresponds to white_count
# 2 corresponds to mid_count
# 3 corresponds to mid_total
# 4 corresponds to current_patch_values
# 5 corresponds to total_count

def reconstruct_from_noisy_patches(input_dict, shape):
    i,j=shape
    values=np.zeros([i,j,6],dtype='int64')
    output=np.zeros([i,j],dtype='int64')
    for patch_key in input_dict.keys():
        
        tlr,tlc,brr,brc=patch_key
        values[tlr:brr,tlc:brc,4]=np.around(input_dict[patch_key])
        update=values[tlr:brr,tlc:brc]
        
        bool_black=update[:,:,4]==0
        bool_white=update[:,:,4]==255
        bool_mid=np.logical_and(update[:,:,4]<255 ,update[:,:,4]>0)
        
        update[bool_black,0]=update[bool_black,0]+1
        update[bool_white,1]=update[bool_white,1]+1
        update[bool_mid,2]=update[bool_mid,2]+1
        update[bool_mid,3]=update[bool_mid,3]+update[bool_mid,4]
        update[:,:,5]=update[:,:,5]+1
        
        values[tlr:brr,tlc:brc]=update[:,:]

    bool_not_b_more_w=np.logical_and(values[:,:,2]==0 ,values[:,:,0]<=values[:,:,1])
    output[bool_not_b_more_w]=255
    output[values[:,:,5]==0]=0
    bool_mid_exists=values[:,:,2]!=0
    output[bool_mid_exists]=np.around(values[bool_mid_exists,3]/values[bool_mid_exists,2])
    output=output.astype('int64')
    return output
