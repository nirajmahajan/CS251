import task2
import utils
from matplotlib import pyplot as plt


img_patches, shape = utils.load('sample_testcase.pkl')
cleaned_img = task2.reconstruct_from_noisy_patches(img_patches, shape)
utils.make_fig(cleaned_img)
# plt.show()
plt.savefig('reconstructed_image_student.png')
# reconstructed_image_student.png should look same as reconstructed_image.png
